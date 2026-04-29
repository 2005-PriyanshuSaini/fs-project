package com.sharma.demo.fooddelivery.util;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

	private final RateLimiter rateLimiter;

	public RateLimitingInterceptor(RateLimiter rateLimiter) {
		this.rateLimiter = rateLimiter;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		// Requirement: remove rate limiting on GET APIs.
		// Keep rate limiting for non-GET requests (e.g., POST /order).
		String method = request.getMethod();
		if (method != null && method.equalsIgnoreCase("GET")) {
			return true;
		}

		String ip = request.getRemoteAddr();
		if (ip == null || ip.isBlank()) {
			ip = "unknown";
		}

		boolean allowed = rateLimiter.isAllowed(ip);
		if (!allowed) {
			response.setStatus(429);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write("{\"message\":\"Rate limit exceeded (5 requests/min).\"}");
			return false;
		}

		return true;
	}
}

