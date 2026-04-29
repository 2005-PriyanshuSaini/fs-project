package com.sharma.demo.library.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class LibraryRateLimiter {

	private final int maxRequestsPerMinute = 5;
	private final long windowMillis = 60_000L;

	// In-memory: IP -> request count
	private final Map<String, Integer> requestCount = new HashMap<>();
	private long windowStart = System.currentTimeMillis();

	public synchronized boolean isAllowed(String ip) {
		long now = System.currentTimeMillis();
		if (now - windowStart >= windowMillis) {
			requestCount.clear();
			windowStart = now;
		}

		int current = requestCount.getOrDefault(ip, 0);
		if (current >= maxRequestsPerMinute) {
			return false;
		}

		requestCount.put(ip, current + 1);
		return true;
	}
}

