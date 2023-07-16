package com.poly.da2.rest;

import com.poly.da2.entity.Order;
import com.fasterxml.jackson.databind.JsonNode;
import com.poly.da2.entity.OrderDetail;
import com.poly.da2.entity.Product;
import com.poly.da2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


	@GetMapping("/orders")
	public List<Order> findByOrderStatus(@RequestParam("status") String status) {
		return orderService.getByOrderStatus(status);
	}
	@GetMapping("/search")
	public List<Order> searchOrders(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "status", required = false) String status) {
		if (username != null && status != null) {
			// Tìm kiếm theo cả username và status
			return orderService.searchByUsernameAndStatus(username, status);
		} else if (username != null) {
			// Tìm kiếm theo username
			return orderService.findByUsername(username, null);
		} else if (status != null) {
			// Tìm kiếm theo status
			return orderService.getByOrderStatus(status);
		} else {
			// Trả về tất cả đơn hàng nếu không có tham số tìm kiếm được cung cấp
			return orderService.getByOrderStatus("Chờ xác nhận");
		}
	}


	@PutMapping("/{id}")
	public Order update(@PathVariable("id") Integer id, @RequestBody Order order) {
		return orderService.update(order);
	}
}
