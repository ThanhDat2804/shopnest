package com.mall.shopnest.security.component;


import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface DynamicSecurityService {

    Map<String, ConfigAttribute> loadDataSource();
}

