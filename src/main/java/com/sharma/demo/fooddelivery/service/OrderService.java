package com.sharma.demo.fooddelivery.service;

import java.util.List;

import com.sharma.demo.fooddelivery.model.Order;

public interface OrderService {

	Order createOrder(List<String> items);

	Order getOrderById(Long id);
}

