package com.mall.shopnest.repository.ums;

import com.mall.shopnest.core.model.ums.UmsRoleMenuRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UmsRoleMenuRelationRepository extends JpaRepository<UmsRoleMenuRelation, Long> {
    List<UmsRoleMenuRelation> findByRoleId(Long roleId);
    List<UmsRoleMenuRelation> findByRoleIdIn(List<Long> roleIds);
    void deleteByRoleId(Long roleId);
}
