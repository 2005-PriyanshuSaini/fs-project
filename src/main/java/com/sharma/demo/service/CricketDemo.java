package com.sharma.demo.service;

import org.springframework.stereotype.Component;

@Component
public class CricketDemo implements SacnMethod {

	@Override
	public String DailyWork() {
		return "now i compeleted my work with 2 hours";
	}
}
