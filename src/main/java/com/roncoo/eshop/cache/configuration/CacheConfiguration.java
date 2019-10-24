package com.roncoo.eshop.cache.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @ClassName CacheConfiguration
 * @Description 缓存配置管理类
 * @Author wuwenxiang
 * @Date 2019-06-25 20:47
 * @Version 1.0
 **/
@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheFactoryBean = new EhCacheManagerFactoryBean();
        cacheFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheFactoryBean.setShared(true);
        return cacheFactoryBean;
    }

    @Bean
    EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
        return new EhCacheCacheManager(bean.getObject());
    }
}
