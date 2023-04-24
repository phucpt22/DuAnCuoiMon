package com.poly.da2.rest;

import com.poly.da2.entities.OrderDetail;
import com.poly.da2.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
public class OrderDetailRestController {
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("/rest/orderdetailall")
	public List<OrderDetail> findAll() {
		return orderDetailService.findAll();
	}

	@GetMapping("/rest/orderdetailall/{orderid}")
	public List<OrderDetail> findAllByOrderId(@PathVariable("orderid")Integer id) {
		return orderDetailService.getOrderDetailByOrdOrderById(id);
	}
}
