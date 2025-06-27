package com.mall.shopnest.repository.ums;

import com.mall.shopnest.core.model.ums.UmsRoleResourceRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UmsRoleResourceRelationRepository extends JpaRepository<UmsRoleResourceRelation, Long> {
    List<UmsRoleResourceRelation> findByRoleId(Long roleId);
    void deleteByRoleId(Long roleId);
}

