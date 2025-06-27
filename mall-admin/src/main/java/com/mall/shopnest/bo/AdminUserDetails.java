package com.mall.shopnest.bo;

import com.mall.shopnest.core.model.ums.UmsAdmin;
import com.mall.shopnest.core.model.ums.UmsResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Backend User Details for Spring Security
 */
@Getter
@RequiredArgsConstructor
public class AdminUserDetails implements UserDetails {
    // Backend user
    private final UmsAdmin umsAdmin;
    // List of owned resources
    private final List<UmsResource> resourceList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Returns the resources currently owned by the user
        return resourceList.stream()
                .map(resource -> new SimpleGrantedAuthority(resource.getId() + ":" + resource.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}
