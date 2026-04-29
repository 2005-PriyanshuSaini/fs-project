package com.sharma.demo.fooddelivery.model;

import java.util.List;

public class OrderResponse {

	private Long id;
	private List<String> items;
	private Double totalAmount;
	private String status;
	private String cacheStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCacheStatus() {
		return cacheStatus;
	}

	public void setCacheStatus(String cacheStatus) {
		this.cacheStatus = cacheStatus;
	}
}

