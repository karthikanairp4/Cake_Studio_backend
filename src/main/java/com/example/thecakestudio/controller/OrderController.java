package com.example.thecakestudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.thecakestudio.dto.BuyNowRequestDTO;
import com.example.thecakestudio.dto.CheckoutDTO;
import com.example.thecakestudio.entity.Order;
import com.example.thecakestudio.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/checkout")
	public Order checkOut(@RequestBody CheckoutDTO checkoutDTO) {
		return orderService.checkOut(checkoutDTO);
		
	}
	
	@PostMapping("/buy-now")
	public Order buyNow(@RequestBody BuyNowRequestDTO buyNowRequestDTO) {
		return orderService.buyNow(buyNowRequestDTO);
	}
	
	@GetMapping("/orders")
	public List<Order> getOrders() {
	    return orderService.getOrders();

	}
	
	@GetMapping("/orders/{id}")
	public Order getOrder(@PathVariable Integer id) {
	    return orderService.getOrder(id);

	}

}
