package com.mall.shopnest.service;

import com.mall.shopnest.core.model.ums.UmsMenu;
import com.mall.shopnest.core.model.ums.UmsResource;
import com.mall.shopnest.core.model.ums.UmsRole;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsRoleService {
    /**
     * Add a role
     */
    int create(UmsRole role);

    /**
     * Update role information
     */
    int update(Long id, UmsRole role);

    /**
     * Delete roles in batch
     */
    int delete(List<Long> ids);

    /**
     * Get all roles
     */
    List<UmsRole> list();

    /**
     * Get a paginated list of roles
     */
    Page<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * Get menus assigned to the admin by admin ID
     */
    List<UmsMenu> getMenuList(Long adminId);

    /**
     * Get menus associated with the role
     */
    List<UmsMenu> listMenu(Long roleId);

    /**
     * Get resources associated with the role
     */
    List<UmsResource> listResource(Long roleId);

    /**
     * Assign menus to the role
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * Assign resources to the role
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);
}
