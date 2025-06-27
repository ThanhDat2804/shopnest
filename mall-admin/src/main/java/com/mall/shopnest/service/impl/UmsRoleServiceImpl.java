package com.mall.shopnest.service.impl;

import com.mall.shopnest.core.model.ums.*;
import com.mall.shopnest.repository.ums.*;
import com.mall.shopnest.service.UmsAdminCacheService;
import com.mall.shopnest.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleRepository roleRepository;

    @Autowired
    private UmsRoleMenuRelationRepository roleMenuRelationRepository;

    @Autowired
    private UmsRoleResourceRelationRepository roleResourceRelationRepository;

    @Autowired
    private UmsMenuRepository menuRepository;

    @Autowired
    private UmsResourceRepository resourceRepository;

    @Autowired
    private UmsAdminRoleRelationRepository adminRoleRelationRepository;

    @Autowired
    private UmsAdminCacheService adminCacheService;

    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        roleRepository.save(role);
        return 1;
    }

    @Override
    public int update(Long id, UmsRole role) {
        role.setId(id);
        roleRepository.save(role);
        return 1;
    }

    @Override
    public int delete(List<Long> ids) {
        roleRepository.deleteAllById(ids);
        adminCacheService.delResourceListByRoleIds(ids);
        return ids.size();
    }

    @Override
    public List<UmsRole> list() {
        return roleRepository.findAll();
    }

    @Override
    public Page<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageRequest pageable = PageRequest.of(pageNum - 1, pageSize); // Convert 1-based pageNum to 0-based
        if (keyword != null && !keyword.trim().isEmpty()) {
            return roleRepository.findByNameContaining(keyword, pageable);
        }
        return roleRepository.findAll(pageable);
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        List<Long> roleIds = adminRoleRelationRepository.findByAdminId(adminId)
                .stream().map(UmsAdminRoleRelation::getRoleId).collect(Collectors.toList());
        return roleMenuRelationRepository.findByRoleIdIn(roleIds)
                .stream().map(r -> menuRepository.findById(r.getMenuId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return roleMenuRelationRepository.findByRoleId(roleId)
                .stream().map(r -> menuRepository.findById(r.getMenuId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        return roleResourceRelationRepository.findByRoleId(roleId)
                .stream().map(r -> resourceRepository.findById(r.getResourceId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        // delete old
        roleMenuRelationRepository.deleteByRoleId(roleId);
        // add new
        List<UmsRoleMenuRelation> relations = menuIds.stream().map(menuId -> {
            UmsRoleMenuRelation r = new UmsRoleMenuRelation();
            r.setRoleId(roleId);
            r.setMenuId(menuId);
            return r;
        }).collect(Collectors.toList());
        roleMenuRelationRepository.saveAll(relations);
        return relations.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        // delete old
        roleResourceRelationRepository.deleteByRoleId(roleId);
        // add new
        List<UmsRoleResourceRelation> relations = resourceIds.stream().map(resourceId -> {
            UmsRoleResourceRelation r = new UmsRoleResourceRelation();
            r.setRoleId(roleId);
            r.setResourceId(resourceId);
            return r;
        }).collect(Collectors.toList());
        roleResourceRelationRepository.saveAll(relations);
        adminCacheService.delResourceListByRole(roleId);
        return relations.size();
    }
}
