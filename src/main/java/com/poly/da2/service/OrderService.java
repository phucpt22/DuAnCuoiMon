package com.poly.da2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.da2.entity.Order;
import com.poly.da2.entity.Product;
import com.poly.da2.entity.TopProduct;
import com.poly.da2.entity.TotalMoneyEachMonth;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService {

	Order create(JsonNode orderData);

	Order findById(Integer id);

	List<Order> findByUsername(String username, String status);

	List<Order> findAll();
	List<Order> getByOrderStatus(String status);

    Order update(Order order);

    List<Order> searchByUsernameAndStatus(String username, String status);
	long getTotalMoneyOrderToday();

	List<TotalMoneyEachMonth> getTotalEachMonthInSpecificYear(int year);

}
