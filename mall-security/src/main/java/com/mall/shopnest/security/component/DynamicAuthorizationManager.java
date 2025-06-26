package com.mall.shopnest.security.component;

import com.mall.shopnest.security.config.IgnoreUrlsConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

//    @Autowired
//    private DynamicSecurityMetadataSource securityDataSource;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        String path = request.getRequestURI();
        PathMatcher pathMatcher = new AntPathMatcher();

        // Allow access directly for whitelisted URLs
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, path)) {
                return new AuthorizationDecision(true);
            }
        }

        // Allow access for CORS preflight requests (HTTP OPTIONS method)
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return new AuthorizationDecision(true);
        }

        // Authorization logic
//        List<ConfigAttribute> configAttributeList = securityDataSource.getConfigAttributesWithPath(path);
//        List<String> needAuthorities = configAttributeList.stream()
//                .map(ConfigAttribute::getAttribute)
//                .collect(Collectors.toList());

        Authentication currentAuth = authentication.get();

        // Check if the user is authenticated
        if (currentAuth.isAuthenticated()) {
            Collection<? extends GrantedAuthority> grantedAuthorities = currentAuth.getAuthorities();
            List<? extends GrantedAuthority> hasAuth = grantedAuthorities.stream()
//                    .filter(item -> needAuthorities.contains(item.getAuthority()))
                    .collect(Collectors.toList());

            // Check if the user has at least one required authority
            if (!hasAuth.isEmpty()) {
                return new AuthorizationDecision(true);
            } else {
                return new AuthorizationDecision(false);
            }
        } else {
            return new AuthorizationDecision(false);
        }
    }
}
