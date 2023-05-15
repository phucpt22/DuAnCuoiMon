package com.poly.da2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.da2.entity.Order;

import java.util.List;

public interface OrderService {

	Order create(JsonNode orderData);

	Order findById(Integer id);

	List<Order> findByUsername(String username);

	List<Order> findAll();

}
