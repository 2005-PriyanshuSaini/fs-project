package com.sharma.demo.library.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LibraryRateLimitingConfig implements WebMvcConfigurer {

	private final LibraryRateLimitingInterceptor interceptor;

	public LibraryRateLimitingConfig(LibraryRateLimitingInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
				.addPathPatterns("/book/**");
	}
}

