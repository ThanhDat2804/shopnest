package com.mall.shopnest.service;

import com.mall.shopnest.core.model.ums.UmsAdmin;
import com.mall.shopnest.core.model.ums.UmsResource;

import java.util.List;

/**
 * Backend user cache management service
 * Created by macro on 2020/3/13.
 */
public interface UmsAdminCacheService {

    /**
     * Delete backend user cache
     */
    void delAdmin(Long adminId);

    /**
     * Delete backend user's resource list cache
     */
    void delResourceList(Long adminId);

    /**
     * Delete backend user resource list cache when a role's related resource information changes
     */
    void delResourceListByRole(Long roleId);

    /**
     * Delete backend user resource list cache when roles' related resource information changes
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * Delete backend user resource list cache when a resource changes
     */
    void delResourceListByResource(Long resourceId);

    /**
     * Get cached backend user information
     */
    UmsAdmin getAdmin(String username);

    /**
     * Set cached backend user information
     */
    void setAdmin(UmsAdmin admin);

    /**
     * Get cached backend user's resource list
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * Set cached backend user's resource list
     */
    void setResourceList(Long adminId, List<UmsResource> resourceList);
}
