package com.poly.da2.rest;

import com.poly.da2.model.Order;
import com.fasterxml.jackson.databind.JsonNode;
import com.poly.da2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class OrderRestController {
	@Autowired
	OrderService orderService;
	
	@PostMapping("/rest/orders")
	public Order create(@RequestBody JsonNode orderData) {
		return orderService.create(orderData);
	}
	
	@GetMapping("/rest/ordersall")
	public List<Order> findAll() {
		return orderService.findAll();
	}
}
