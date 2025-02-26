package com.example.RestAPI.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager itemsCacheManager() {
        return new ConcurrentMapCacheManager("items");
    }

    @Bean
    public CacheManager usersCacheManager() {
        return new ConcurrentMapCacheManager("users");
    }

}
