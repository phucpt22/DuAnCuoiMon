package com.poly.da2.rest;

import com.poly.da2.entity.Order;
import com.fasterxml.jackson.databind.JsonNode;
import com.poly.da2.entity.TopProduct;
import com.poly.da2.entity.TotalMoneyEachMonth;
import com.poly.da2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orders")
public class OrderRestController {
	@Autowired
	OrderService orderService;
	
	@PostMapping()
	public ResponseEntity<Order> create(@RequestBody JsonNode orderData) {
		try {
			Order order = orderService.create(orderData);
			return new ResponseEntity<>(order, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("")
	public List<Order> getAll() {
		return orderService.findAll();
	}

	@GetMapping("total-money")
	public long getTotalMoneyToday(){
		return orderService.getTotalMoneyOrderToday();
	}

	@GetMapping("total-money-every-mounth{year}")
	public List<TotalMoneyEachMonth> getTotalEachMonthInSpecificYear(@PathVariable("year") int year){
			return orderService.getTotalEachMonthInSpecificYear(year);
	}

}
