package com.poly.da2.service.impl;

import com.poly.da2.repository.OrderDetailDAO;
import com.poly.da2.entities.OrderDetail;
import com.poly.da2.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	OrderDetailDAO orderDetailDAO;
	
	@Override
	public List<OrderDetail> findAll() {
		return orderDetailDAO.findAll();
	}

	@Override
	public List<OrderDetail> getOrderDetailByOrdOrderById(Integer orderId) {
		return orderDetailDAO.findOrderDetailByOrdOrderById(orderId);
	}


}
