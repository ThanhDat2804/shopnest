package com.mall.shopnest.repository.ums;

import com.mall.shopnest.core.model.ums.UmsAdminLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UmsAdminLoginLogRepository extends JpaRepository<UmsAdminLoginLog, Long> {
}

