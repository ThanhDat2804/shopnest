package com.mall.shopnest.service;

import com.mall.shopnest.core.model.ums.UmsResource;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Backend Resource Management Service
 * Created by macro on 2020/2/2.
 */
public interface UmsResourceService {

    /**
     * Add a new resource
     */
    int create(UmsResource umsResource);

    /**
     * Update an existing resource
     */
    int update(Long id, UmsResource umsResource);

    /**
     * Get resource details by ID
     */
    UmsResource getItem(Long id);

    /**
     * Delete a resource by ID
     */
    int delete(Long id);

    /**
     * Paginated query for resources
     */
    Page<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);


    /**
     * Get all resources
     */
    Page<UmsResource> listAll();
}
