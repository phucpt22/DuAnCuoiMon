package com.poly.da2.service.impl;

import com.poly.da2.repository.OrderDetailRepository;
import com.poly.da2.model.OrderDetail;
import com.poly.da2.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
    OrderDetailRepository orderDetailRepository;
	
	@Override
	public List<OrderDetail> findAll() {
		return orderDetailRepository.findAll();
	}

	@Override
	public List<OrderDetail> getOrderDetailByOrdOrderById(Integer orderId) {
		return orderDetailRepository.findOrderDetailByOrdOrderById(orderId);
	}


}
