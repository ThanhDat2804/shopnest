package com.mall.shopnest.repository.ums;

import com.mall.shopnest.core.model.ums.UmsAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UmsAdminRepository extends JpaRepository<UmsAdmin, Long> {
    Optional<UmsAdmin> findByUsername(String username);
    Page<UmsAdmin> findByUsernameContainingOrNickNameContaining(String username, String nickname, Pageable pageable);
}
