package com.poly.da2.service.impl;

import com.poly.da2.entity.Category;
import com.poly.da2.repository.ProductRepository;
import com.poly.da2.entity.Product;
import com.poly.da2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
    ProductRepository productRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public Product findById(Integer id) {
		return productRepository.findById(id).get();
	}

	@Override
	public Page<Product> findByCategoryId(String cid, Pageable pageable) {
		return productRepository.getByCategoryId(cid,pageable);
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
	public Page<Product> findByPrice(double min, double max, Pageable pageable) {
		return productRepository.findByPrice(min,max,pageable);
	}


	@Override
	public Page<Product> searchProducts(String name, Pageable pageable) {
		return productRepository.getByName(name,pageable);
	}

	@Override
	public List<Product> sanPhamLienQuan(String cid, Pageable pageable) {
		return productRepository.SanPhamLienQuan(cid,pageable);
	}


	@Override
	//@Transactional(readOnly = true)
	public List<Product> sanphambanchay() {
		return productRepository.sanphambanchay();
	}



}
