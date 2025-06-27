package com.mall.shopnest.portal.service.impl;

import com.mall.shopnest.portal.domain.MemberDetails;
import com.mall.shopnest.exception.Asserts;
import com.mall.shopnest.core.model.ums.UmsMember;
import com.mall.shopnest.portal.repository.UmsMemberLevelRepository;
import com.mall.shopnest.portal.repository.UmsMemberRepository;
import com.mall.shopnest.portal.service.UmsMemberCacheService;
import com.mall.shopnest.portal.service.UmsMemberSevice;
import com.mall.shopnest.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class UmsMemberServiceImpl implements UmsMemberSevice {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsMemberRepository memberRepository;

    @Autowired
    private UmsMemberLevelRepository memberLevelRepository;

    @Autowired
    private UmsMemberCacheService memberCacheService;

    @Value("${redis.key.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    @Value("${redis.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Override
    public UmsMember getByUsername(String username) {
        UmsMember member = memberCacheService.getMember(username);
        if (member != null) return member;

        return memberRepository.findByUsername(username).map(m -> {
            memberCacheService.setMember(m);
            return m;
        }).orElse(null);
    }

    @Override
    public UmsMember getById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public void register(String username, String password, String telephone, String authCode) {
        if (!verifyAuthCode(authCode, telephone)) {
            Asserts.fail("Verification code is incorrect");
        }

        if (memberRepository.findByUsername(username).isPresent() || memberRepository.findByPhone(telephone).isPresent()) {
            Asserts.fail("User already exists");
        }

        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(username);
        umsMember.setPhone(telephone);
        umsMember.setPassword(passwordEncoder.encode(password));
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);

        memberLevelRepository.findFirstByDefaultStatus(1)
                .ifPresent(level -> umsMember.setMemberLevelId(level.getId()));

        memberRepository.save(umsMember);
        umsMember.setPassword(null);
    }

    @Override
    public String generateAuthCode(String telephone) {
        String authCode = String.format("%06d", new Random().nextInt(999999));
        memberCacheService.setAuthCode(telephone, authCode);
        return authCode;
    }

    @Override
    public void updatePassword(String telephone, String password, String authCode) {
        UmsMember umsMember = memberRepository.findByPhone(telephone)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));


        if (!verifyAuthCode(authCode, telephone)) {
            Asserts.fail("Verification code is incorrect");
        }

        umsMember.setPassword(passwordEncoder.encode(password));
        memberRepository.save(umsMember);
        memberCacheService.delMember(umsMember.getId());
    }

    @Override
    public UmsMember getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
        return memberDetails.getUmsMember();
    }

    @Override
    public void updateIntegration(Long id, Integer integration) {
        UmsMember umsMember = memberRepository.findById(id).orElse(null);
        if (umsMember != null) {
            umsMember.setIntegration(integration);
            memberRepository.save(umsMember);
            memberCacheService.delMember(id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsMember member = getByUsername(username);
        if (member != null) {
            return new MemberDetails(member);
        }
        throw new UsernameNotFoundException("Incorrect username or password");
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("Password is incorrect");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("Login exception: {}", e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }

    private boolean verifyAuthCode(String authCode, String telephone) {
        if (authCode == null || authCode.isEmpty()) {
            return false;
        }
        String realAuthCode = memberCacheService.getAuthCode(telephone);
        return authCode.equals(realAuthCode);
    }
}
