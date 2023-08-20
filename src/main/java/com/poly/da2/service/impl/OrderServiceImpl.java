package com.poly.da2.service.impl;

import com.poly.da2.entity.*;
import com.poly.da2.repository.AccountRepository;
import com.poly.da2.repository.OrderRepository;
import com.poly.da2.repository.OrderDetailRepository;
import com.poly.da2.repository.UserRepository;
import com.poly.da2.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	public Order create(JsonNode orderData) {

		ObjectMapper mapper=new ObjectMapper();
		Order order=mapper.convertValue(orderData, Order.class);
		int idUser = orderData.get("user").get("id").asInt();
		Userss user = userRepository.findOneById(idUser);
		order.setUser(user);
		orderRepository.save(order);

		TypeReference<List<OrderDetail>> type=new TypeReference<List<OrderDetail>>(){};
		List<OrderDetail> details=mapper.convertValue(orderData.get("orderDetails"), type).stream()
				.peek(d->d.setOrder(order)).collect(Collectors.toList());
		orderDetailRepository.saveAll(details);
		return order;
	}

	@Override
	public Order findById(Integer id) {
		return orderRepository.findById(id).get();
	}

	@Override
	public List<Order> findByUsername(String username, String status) {
		return orderRepository.findByUsername(username,status);
	}


	@Override
	public List<Order> findAll() {
		return orderRepository.findAlldesc();
	}

	@Override
	public List<Order> getByOrderStatus(String status) {
		return orderRepository.getByOderStatus(status);
	}

	@Override
	public Order update(Order order) {
		return orderRepository.save(order);
	}
	@Override
	public List<Order> searchByUsernameAndStatus(String username, String status) {
		return orderRepository.findByUsernameAndStatus(username, status);
	}


	@Override
	public long getTotalMoneyOrderToday() {
		try{
			return orderRepository.getTotalMoneyOrderToday();
		}catch (Exception e){
			return 0;
		}
	}

	@Override
	public List<TotalMoneyEachMonth> getTotalEachMonthInSpecificYear(int year) {
		return orderRepository.totalPriceSpecificYear(year);
	}


}
