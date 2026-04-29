package com.sharma.demo.fooddelivery.controller;

import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharma.demo.fooddelivery.model.Order;
import com.sharma.demo.fooddelivery.model.OrderResponse;
import com.sharma.demo.fooddelivery.service.OrderService;
import com.sharma.demo.fooddelivery.util.LoadBalancer;

@RestController
@RequestMapping("/fooddelivery")
public class OrderController {

	private final LoadBalancer loadBalancer;
	private final CacheManager cacheManager;

	public OrderController(LoadBalancer loadBalancer, CacheManager cacheManager) {
		this.loadBalancer = loadBalancer;
		this.cacheManager = cacheManager;
	}

	@PostMapping("/order")
	public ResponseEntity<Order> createOrder(@RequestBody Order request) {
		List<String> items = request.getItems();
		OrderService service = loadBalancer.next();
		Order created = service.createOrder(items);

		// Evict cached GET results after creating a new order.
		Cache cache = cacheManager.getCache("orders");
		if (cache != null) {
			cache.clear();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping("/order/{id}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
		Cache cache = cacheManager.getCache("orders");
		OrderService service = loadBalancer.next();

		// Manual cache access so we can attach HIT/MISS in the response.
		if (cache != null) {
			Order cached = cache.get(id, Order.class);
			if (cached != null) {
				OrderResponse resp = toResponse(cached, "HIT");
				return ResponseEntity.ok()
						.header("X-Cache", "HIT")
						.body(resp);
			}
		}

		Order order = service.getOrderById(id);
		if (cache != null) {
			cache.put(id, order);
		}

		OrderResponse resp = toResponse(order, "MISS");
		return ResponseEntity.ok()
				.header("X-Cache", "MISS")
				.body(resp);
	}

	private static OrderResponse toResponse(Order order, String cacheStatus) {
		OrderResponse resp = new OrderResponse();
		resp.setId(order.getId());
		resp.setItems(order.getItems());
		resp.setTotalAmount(order.getTotalAmount());
		resp.setStatus(order.getStatus());
		resp.setCacheStatus(cacheStatus);
		return resp;
	}
}

