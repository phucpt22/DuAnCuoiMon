package com.poly.da2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.da2.entity.Order;
import com.poly.da2.entity.Product;

import java.util.List;

public interface OrderService {

	Order create(JsonNode orderData);

	Order findById(Integer id);

	List<Order> findByUsername(String username, String status);

	List<Order> findAll();
	List<Order> getByOrderStatus(String status);

    Order update(Order order);

    List<Order> searchByUsernameAndStatus(String username, String status);
}
