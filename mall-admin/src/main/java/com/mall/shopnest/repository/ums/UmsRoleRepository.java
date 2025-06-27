package com.mall.shopnest.repository.ums;

import com.mall.shopnest.core.model.ums.UmsRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UmsRoleRepository extends JpaRepository<UmsRole, Long> {

    @Query("SELECT r FROM UmsRole r WHERE r.id IN (" +
            "SELECT arr.roleId FROM UmsAdminRoleRelation arr WHERE arr.adminId = :adminId)")
    List<UmsRole> findRolesByAdminId(@Param("adminId") Long adminId);
    Page<UmsRole> findByNameContaining(String name, Pageable pageable);
}
