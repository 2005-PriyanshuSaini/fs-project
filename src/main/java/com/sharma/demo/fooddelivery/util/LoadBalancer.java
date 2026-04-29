package com.sharma.demo.fooddelivery.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.sharma.demo.fooddelivery.service.OrderService;
import com.sharma.demo.fooddelivery.service.OrderService1;
import com.sharma.demo.fooddelivery.service.OrderService2;

@Component
public class LoadBalancer {

	private final OrderService1 orderService1;
	private final OrderService2 orderService2;
	private final AtomicInteger counter = new AtomicInteger(0);

	public LoadBalancer(OrderService1 orderService1, OrderService2 orderService2) {
		this.orderService1 = orderService1;
		this.orderService2 = orderService2;
	}

	public OrderService next() {
		int value = counter.getAndIncrement();
		return (value % 2 == 0) ? orderService1 : orderService2;
	}
}

