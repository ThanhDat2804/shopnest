package com.mall.shopnest.portal.repository;

import com.mall.shopnest.model.UmsMemberLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UmsMemberLevelRepository extends JpaRepository<UmsMemberLevel, Long> {
    Optional<UmsMemberLevel> findFirstByDefaultStatus(Integer defaultStatus);
}

