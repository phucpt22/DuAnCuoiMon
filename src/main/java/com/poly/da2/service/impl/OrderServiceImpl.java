package com.poly.da2.service.impl;

import com.poly.da2.entity.Userss;
import com.poly.da2.repository.AccountRepository;
import com.poly.da2.repository.OrderRepository;
import com.poly.da2.repository.OrderDetailRepository;
import com.poly.da2.entity.Order;
import com.poly.da2.entity.OrderDetail;
import com.poly.da2.repository.UserRepository;
import com.poly.da2.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Override
	public Order create(JsonNode orderData) {

		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.convertValue(orderData, Order.class);

		// Set the User based on the ID submitted with the order form
		Integer userId = orderData.get("user").get("id").asInt();
		Userss user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
		order.setUser(user);

		orderRepository.save(order);
		TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {};
		List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"), type).stream()
				.peek(d -> d.setOrder(order)).collect(Collectors.toList());
		orderDetailRepository.saveAll(details);

		return order;
	}

	@Override
	public Order findById(Integer id) {
		return orderRepository.findById(id).get();
	}

	@Override
	public List<Order> findByUsername(String username) {
		return orderRepository.findByUsername(username);
	}


	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	
	
}
