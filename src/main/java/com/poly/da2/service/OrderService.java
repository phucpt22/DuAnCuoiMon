package com.poly.da2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.da2.entity.Order;
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

	long getTotalMoneyOrderToday();

	List<TotalMoneyEachMonth> getTotalEachMonthInSpecificYear(int year);

}
