package com.mall.shopnest.portal.config;

import com.mall.shopnest.portal.service.UmsMemberSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class MallSecurityConfig {

    @Autowired
    private UmsMemberSevice memberService;


    @Bean
    public UserDetailsService userDetailsService() {
        // Get the login user information
        return username -> memberService.loadUserByUsername(username);
    }
}
