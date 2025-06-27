package com.mall.shopnest.service.impl;

import com.mall.shopnest.bo.AdminUserDetails;
import com.mall.shopnest.core.model.ums.*;
import com.mall.shopnest.dto.UmsAdminParam;
import com.mall.shopnest.dto.UpdateAdminPasswordParam;
import com.mall.shopnest.exception.Asserts;
import com.mall.shopnest.repository.ums.*;
import com.mall.shopnest.security.util.JwtTokenUtil;
import com.mall.shopnest.security.util.SpringUtil;
import com.mall.shopnest.service.UmsAdminCacheService;
import com.mall.shopnest.service.UmsAdminService;
import com.mall.shopnest.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsAdminRepository adminRepository;

    @Autowired
    private UmsAdminRoleRelationRepository adminRoleRelationRepository;

    @Autowired
    private UmsRoleRepository roleRepository;

    @Autowired
    private UmsResourceRepository resourceRepository;

    @Autowired
    private UmsAdminLoginLogRepository loginLogRepository;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdmin admin = getCacheService().getAdmin(username);
        if (admin != null) return admin;

        Optional<UmsAdmin> adminOpt = adminRepository.findByUsername(username);
        if (adminOpt.isPresent()) {
            admin = adminOpt.get();
            getCacheService().setAdmin(admin);
            return admin;
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);

        if (adminRepository.findByUsername(umsAdmin.getUsername()).isPresent()) {
            return null;
        }

        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        return adminRepository.save(umsAdmin);
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail("Incorrect password");
            }
            if (!userDetails.isEnabled()) {
                Asserts.fail("Account is disabled");
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            LOGGER.warn("Login exception: {}", e.getMessage());
        }
        return token;
    }

    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (admin == null) return;
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(RequestUtil.getRequestIp(request));
        loginLogRepository.save(loginLog);
    }

    private void updateLoginTimeByUsername(String username) {
        Optional<UmsAdmin> optional = adminRepository.findByUsername(username);
        optional.ifPresent(admin -> {
            admin.setLoginTime(new Date());
            adminRepository.save(admin);
        });
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public UmsAdmin getItem(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Page<UmsAdmin> list(String keyword, Pageable pageable) {
        if (StringUtils.hasText(keyword)) {
            return adminRepository.findByUsernameContainingOrNickNameContaining(keyword, keyword, pageable);
        }
        return adminRepository.findAll(pageable);
    }

    @Override
    public int update(Long id, UmsAdmin admin) {
        admin.setId(id);
        UmsAdmin rawAdmin = adminRepository.findById(id).orElse(null);
        if (rawAdmin == null) return 0;

        if (Objects.equals(rawAdmin.getPassword(), admin.getPassword())) {
            admin.setPassword(null);
        } else if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        } else {
            admin.setPassword(null);
        }
        adminRepository.save(admin);
        getCacheService().delAdmin(id);
        return 1;
    }

    @Override
    public int delete(Long id) {
        adminRepository.deleteById(id);
        getCacheService().delAdmin(id);
        getCacheService().delResourceList(id);
        return 1;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        adminRoleRelationRepository.deleteByAdminId(adminId);

        if (roleIds != null && !roleIds.isEmpty()) {
            List<UmsAdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsAdminRoleRelation relation = new UmsAdminRoleRelation();
                relation.setAdminId(adminId);
                relation.setRoleId(roleId);
                list.add(relation);
            }
            adminRoleRelationRepository.saveAll(list);
        }
        getCacheService().delResourceList(adminId);
        return roleIds != null ? roleIds.size() : 0;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return roleRepository.findRolesByAdminId(adminId);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        List<UmsResource> resourceList = getCacheService().getResourceList(adminId);
        if (resourceList != null && !resourceList.isEmpty()) {
            return resourceList;
        }
        resourceList = resourceRepository.findResourcesByAdminId(adminId);
        if (resourceList != null && !resourceList.isEmpty()) {
            getCacheService().setResourceList(adminId, resourceList);
        }
        return resourceList;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        if (param.getUsername() == null || param.getOldPassword() == null || param.getNewPassword() == null) {
            return -1;
        }
        Optional<UmsAdmin> optional = adminRepository.findByUsername(param.getUsername());
        if (optional.isEmpty()) {
            return -2;
        }
        UmsAdmin admin = optional.get();
        if (!passwordEncoder.matches(param.getOldPassword(), admin.getPassword())) {
            return -3;
        }
        admin.setPassword(passwordEncoder.encode(param.getNewPassword()));
        adminRepository.save(admin);
        getCacheService().delAdmin(admin.getId());
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<UmsResource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin, resourceList);
        }
        throw new UsernameNotFoundException("Username or password incorrect");
    }

    @Override
    public UmsAdminCacheService getCacheService() {
        return SpringUtil.getBean(UmsAdminCacheService.class);
    }

    @Override
    public void logout(String username) {
        UmsAdmin admin = getCacheService().getAdmin(username);
        getCacheService().delAdmin(admin.getId());
        getCacheService().delResourceList(admin.getId());
    }
}
