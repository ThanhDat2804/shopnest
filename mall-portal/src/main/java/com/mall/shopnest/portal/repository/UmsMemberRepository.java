package com.mall.shopnest.portal.repository;

import com.mall.shopnest.model.UmsMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UmsMemberRepository extends JpaRepository<UmsMember, Long> {
    Optional<UmsMember> findByUsername(String username);
    Optional<UmsMember> findByPhone(String phone);
}
