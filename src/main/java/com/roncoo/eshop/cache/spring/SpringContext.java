package com.roncoo.eshop.cache.spring;


import org.springframework.context.ApplicationContext;

/**
 * @ClassName SpringContext
 * @Description Spring上下文
 * @Author wuwenxiang
 * @Date 2019-07-16 22:30
 * @Version 1.0
 **/
public class SpringContext {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContext.applicationContext = applicationContext;
    }
}
