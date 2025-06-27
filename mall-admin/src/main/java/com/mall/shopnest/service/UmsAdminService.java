package com.mall.shopnest.service;


import com.mall.shopnest.core.model.ums.UmsAdmin;
import com.mall.shopnest.core.model.ums.UmsResource;
import com.mall.shopnest.core.model.ums.UmsRole;
import com.mall.shopnest.dto.UmsAdminParam;
import com.mall.shopnest.dto.UpdateAdminPasswordParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UmsAdminService {
    /**
     * Retrieve a backend admin by username
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * Register function
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * Login function
     * @param username Username
     * @param password Password
     * @return Generated JWT token
     */
    String login(String username, String password);

    /**
     * Refresh token function
     * @param oldToken The old token
     */
    String refreshToken(String oldToken);

    /**
     * Retrieve a user by user ID
     */
    UmsAdmin getItem(Long id);

    /**
     * Query users by username or nickname with pagination
     */
    Page<UmsAdmin> list(String keyword, Pageable pageable);

    /**
     * Update specified user information
     */
    int update(Long id, UmsAdmin admin);

    /**
     * Delete specified user
     */
    int delete(Long id);

    /**
     * Update user-role relationships
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

    /**
     * Retrieve roles associated with a user
     */
    List<UmsRole> getRoleList(Long adminId);

    /**
     * Retrieve accessible resources for a specified user
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * Update password
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * Retrieve user information
     */
    UserDetails loadUserByUsername(String username);

    /**
     * Retrieve cache service
     */
    UmsAdminCacheService getCacheService();

    /**
     * Logout function
     * @param username Username
     */
    void logout(String username);
}
