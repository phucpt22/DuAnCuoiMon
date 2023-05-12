package com.poly.da2.service.impl;

import com.poly.da2.repository.ProductRepository;
import com.poly.da2.entities.Product;
import com.poly.da2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
    ProductRepository productRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product findById(Integer id) {
		return productRepository.findById(id).get();
	}

	@Override
	public List<Product> findByCategoryId(String cid) {
		return productRepository.getByCategoryId(cid);
	}

	@Override
	public Product create(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product update(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void delete(Integer id) {
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> findByPrice(double min, double max) {
		return productRepository.findByPrice(min,max);
	}

	@Override
	public List<Product> findByName(String name) {
		return productRepository.findSanPhamByName(name);
	}


}
