package com.mall.shopnest.repository.ums;

import com.mall.shopnest.core.model.ums.UmsResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UmsResourceRepository extends JpaRepository<UmsResource, Long>, JpaSpecificationExecutor<UmsResource> {

    @Query("SELECT DISTINCT res FROM UmsResource res WHERE res.id IN (" +
            "SELECT rrr.resourceId FROM UmsRoleResourceRelation rrr WHERE rrr.roleId IN (" +
            "SELECT arr.roleId FROM UmsAdminRoleRelation arr WHERE arr.adminId = :adminId))")
    List<UmsResource> findResourcesByAdminId(@Param("adminId") Long adminId);
}
