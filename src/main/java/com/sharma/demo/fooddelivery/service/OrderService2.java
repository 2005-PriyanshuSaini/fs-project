package com.sharma.demo.fooddelivery.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sharma.demo.fooddelivery.model.Order;
import com.sharma.demo.fooddelivery.model.Restaurant;
import com.sharma.demo.fooddelivery.repository.OrderRepository;

@Service
public class OrderService2 implements OrderService {

	private final OrderRepository orderRepository;

	public OrderService2(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public Order createOrder(List<String> items) {
		if (items == null) {
			items = new ArrayList<>();
		}

		// Dummy calculation (service2 uses larger multiplier)
		double menuSize = Restaurant.getInstance().getMenu().size();
		double totalAmount = items.size() * 60.0 + menuSize;

		Order order = new Order();
		order.setItems(items);
		order.setTotalAmount(totalAmount);
		order.setStatus("PLACED");

		return orderRepository.save(order);
	}

	@Override
	public Order getOrderById(Long id) {
		Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
		return order;
	}
}

