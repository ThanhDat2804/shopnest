package com.mall.shopnest.repository.ums;

import com.mall.shopnest.core.model.ums.UmsAdminRoleRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UmsAdminRoleRelationRepository extends JpaRepository<UmsAdminRoleRelation, Long> {

    // Get all role relations by admin ID
    List<UmsAdminRoleRelation> findByAdminId(Long adminId);

    // Delete all role relations for an admin
    void deleteByAdminId(Long adminId);

    // Get all role relations by a single role ID
    List<UmsAdminRoleRelation> findByRoleId(Long roleId);

    // Get all role relations by a list of role IDs
    List<UmsAdminRoleRelation> findByRoleIdIn(List<Long> roleIds);

    // Get admin IDs by resource ID through role-resource relation
    @Query("SELECT arr.adminId FROM UmsAdminRoleRelation arr " +
            "WHERE arr.roleId IN (" +
            "SELECT rrr.roleId FROM UmsRoleResourceRelation rrr WHERE rrr.resourceId = :resourceId)")
    List<Long> findAdminIdsByResourceId(@Param("resourceId") Long resourceId);
}
