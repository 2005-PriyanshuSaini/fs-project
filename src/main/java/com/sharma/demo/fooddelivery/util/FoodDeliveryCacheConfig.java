package com.sharma.demo.fooddelivery.util;

import java.util.Collections;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class FoodDeliveryCacheConfig {

	@Bean
	CacheManager cacheManager() {
		// Simple in-memory cache for order GETs.
		return new ConcurrentMapCacheManager("orders");
	}
}

