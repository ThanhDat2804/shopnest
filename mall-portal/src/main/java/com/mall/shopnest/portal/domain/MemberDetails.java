package com.mall.shopnest.portal.domain;

import com.mall.shopnest.core.model.ums.UmsMember;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * Custom implementation of UserDetails for member authentication.
 */
@Getter
@RequiredArgsConstructor
public class MemberDetails implements UserDetails {

    private final UmsMember umsMember;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the user's authorities (currently hardcoded as "TEST")
        return Arrays.asList(new SimpleGrantedAuthority("TEST"));
    }

    @Override
    public String getPassword() {
        return umsMember.getPassword();
    }

    @Override
    public String getUsername() {
        return umsMember.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Account never expires
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Account never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credentials never expire
    }

    @Override
    public boolean isEnabled() {
        return umsMember.getStatus() == 1; // Status 1 = enabled
    }
}

