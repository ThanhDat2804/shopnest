package com.mall.shopnest.portal.service;

import com.mall.shopnest.core.model.ums.UmsMember;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;

public interface UmsMemberSevice {

    UmsMember getByUsername(String username);

    UmsMember getById(Long id);

    @Transactional
    void register(String username, String password, String telephone, String authCode);

    String generateAuthCode(String telephone);

    @Transactional
    void updatePassword(String telephone, String password, String authCode);

    UmsMember getCurrentMember();

    void updateIntegration(Long id,Integer integration);

    UserDetails loadUserByUsername(String username);

    String login(String username, String password);

    String refreshToken(String token);
}
