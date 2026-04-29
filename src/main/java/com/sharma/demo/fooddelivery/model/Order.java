package com.sharma.demo.fooddelivery.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "fd_orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ElementCollection
	@CollectionTable(name = "fd_order_items", joinColumns = @JoinColumn(name = "order_id"))
	@Column(name = "item")
	private List<String> items = new ArrayList<>();

	private Double totalAmount;

	private String status;

	@Transient
	private String cacheStatus;

	public Order() {
	}

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

	@JsonProperty("cacheStatus")
	public String getCacheStatus() {
		return cacheStatus;
	}

	public void setCacheStatus(String cacheStatus) {
		this.cacheStatus = cacheStatus;
	}
}

