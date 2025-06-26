package com.mall.shopnest.security.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Utility class to access Spring ApplicationContext and retrieve beans manually.
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    // Get the ApplicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // Called by Spring to inject the ApplicationContext
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    // Get a bean by name
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // Get a bean by class type
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // Get a bean by name and class type
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
