package com.poly.da2.rest;

import com.poly.da2.entity.OrderDetail;
import com.poly.da2.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orders")
public class OrderDetailRestController {
	@Autowired
	OrderDetailService orderDetailService;

	@GetMapping("{orderid}")
	public List<OrderDetail> findAllByOrderId(@PathVariable("orderid")Integer id) {
		return orderDetailService.getOrderDetailByOrdOrderById(id);
	}
}
