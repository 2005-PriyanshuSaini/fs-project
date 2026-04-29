package com.sharma.demo.fooddelivery.model;

import java.util.Arrays;
import java.util.List;

public class Restaurant {

	private static final Restaurant INSTANCE = new Restaurant();

	private Restaurant() {
	}

	public static Restaurant getInstance() {
		return INSTANCE;
	}

	public List<String> getMenu() {
		return Arrays.asList("Burger", "Pizza", "Sandwich", "Salad");
	}
}

