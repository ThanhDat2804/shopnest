package com.mall.shopnest.service.impl;

import com.mall.shopnest.core.model.ums.UmsResource;
import com.mall.shopnest.repository.ums.UmsResourceRepository;
import com.mall.shopnest.service.UmsAdminCacheService;
import com.mall.shopnest.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UmsResourceServiceImpl implements UmsResourceService {

    @Autowired
    private UmsResourceRepository resourceRepository;

    @Autowired
    private UmsAdminCacheService adminCacheService;

    @Override
    public int create(UmsResource umsResource) {
        umsResource.setCreateTime(new Date());
        resourceRepository.save(umsResource);
        return 1;
    }

    @Override
    public int update(Long id, UmsResource umsResource) {
        umsResource.setId(id);
        resourceRepository.save(umsResource);
        adminCacheService.delResourceListByResource(id);
        return 1;
    }

    @Override
    public UmsResource getItem(Long id) {
        return resourceRepository.findById(id).orElse(null);
    }

    @Override
    public int delete(Long id) {
        resourceRepository.deleteById(id);
        adminCacheService.delResourceListByResource(id);
        return 1;
    }

    @Override
    public List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        Specification<UmsResource> spec = (root, query, cb) -> {
            var predicates = cb.conjunction();
            if (categoryId != null) {
                predicates = cb.and(predicates, cb.equal(root.get("categoryId"), categoryId));
            }
            if (nameKeyword != null && !nameKeyword.isEmpty()) {
                predicates = cb.and(predicates, cb.like(root.get("name"), "%" + nameKeyword + "%"));
            }
            if (urlKeyword != null && !urlKeyword.isEmpty()) {
                predicates = cb.and(predicates, cb.like(root.get("url"), "%" + urlKeyword + "%"));
            }
            return predicates;
        };

        return resourceRepository.findAll(spec, PageRequest.of(pageNum - 1, pageSize)).getContent();
    }

    @Override
    public List<UmsResource> listAll() {
        return resourceRepository.findAll();
    }
}
