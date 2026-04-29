package com.sharma.demo.fooddelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharma.demo.fooddelivery.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

