package com.sharma.demo.fooddelivery.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharma.demo.fooddelivery.model.Order;
import com.sharma.demo.fooddelivery.service.OrderService;
import com.sharma.demo.fooddelivery.util.LoadBalancer;

@RestController
@RequestMapping("/fooddelivery")
public class OrderController {

	private final LoadBalancer loadBalancer;

	public OrderController(LoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}

	@PostMapping("/order")
	@CacheEvict(cacheNames = "orders", allEntries = true)
	public ResponseEntity<Order> createOrder(@RequestBody Order request) {
		List<String> items = request.getItems();
		OrderService service = loadBalancer.next();
		Order created = service.createOrder(items);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping("/order/{id}")
	@Cacheable(cacheNames = "orders", key = "#id")
	public Order getOrderById(@PathVariable Long id) {
		OrderService service = loadBalancer.next();
		return service.getOrderById(id);
	}
}

