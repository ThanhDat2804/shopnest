//package com.mall.shopnest.component;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//
//import jakarta.annotation.PostConstruct;
//import java.util.*;
//
//@Component
//public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//
//    private static Map<String, ConfigAttribute> configAttributeMap = null;
//
//    @Autowired
//    private DynamicSecurityService dynamicSecurityService;
//
//    @PostConstruct
//    public void loadDataSource() {
//        configAttributeMap = dynamicSecurityService.loadDataSource();
//    }
//
//    public void clearDataSource() {
//        configAttributeMap.clear();
//        configAttributeMap = null;
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
//        // Get the current request path
//        FilterInvocation filterInvocation = (FilterInvocation) o;
//        String path = filterInvocation.getHttpRequest().getRequestURI();
//        return getConfigAttributesWithPath(path);
//    }
//
//    // Retrieve permissions for the current request path
//    public List<ConfigAttribute> getConfigAttributesWithPath(String path) {
//        if (configAttributeMap == null) this.loadDataSource();
//        List<ConfigAttribute> configAttributes = new ArrayList<>();
//        PathMatcher pathMatcher = new AntPathMatcher();
//        Iterator<String> iterator = configAttributeMap.keySet().iterator();
//        // Retrieve resources required for the path
//        while (iterator.hasNext()) {
//            String pattern = iterator.next();
//            if (pathMatcher.match(pattern, path)) {
//                configAttributes.add(configAttributeMap.get(pattern));
//            }
//        }
//        // Return empty collection if no permissions are set for the request
//        return configAttributes;
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }
//}