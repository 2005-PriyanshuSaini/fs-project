package com.sharma.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharma.demo.service.SacnMethod;

@RestController
@RequestMapping("/demo") // Added this to prevent endpoint collisions with RunController
public class ControllerDemo {
	
	private SacnMethod scan;
	
	@Autowired
	public ControllerDemo(SacnMethod thescan){
		this.scan = thescan;
	}

	@GetMapping("/dailywork")
	@PreAuthorize("hasRole('ADMIN')")
	public String DailyWork() {
		return scan.DailyWork();
	}
	
	@GetMapping("/")
	@PreAuthorize("hasRole('USER')")
	public String Home() {
		return "ControllerDem"; 
	}
	
	@GetMapping("/about")
	@PreAuthorize("hasRole('USER')")
	public String About() {
		return "This is a About Page";
	}
	
	@GetMapping("/help")
	@PreAuthorize("hasRole('USER')")
	public String Help() {
		return "He needs a help"; 
	}
}