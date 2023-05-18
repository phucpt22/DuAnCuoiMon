package com.poly.da2.service.impl;

import com.poly.da2.repository.ProductRepository;
import com.poly.da2.entity.Product;
import com.poly.da2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	public List<Product> findByPrice(double min, double max) {
		return productRepository.findByPrice(min,max);
	}

	@Override
	public List<Product> findByName(String searchTerm, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("name"));
		if (searchTerm != null && !searchTerm.isBlank()) {
			return productRepository.findByNameContainingIgnoreCase(searchTerm, pageable).getContent();
		} else {
			return productRepository.findAll(pageable).getContent();
		}
	}

	@Override
	public Page<Product> searchProducts(String name, Pageable pageable) {
		return productRepository.getByName(name,pageable);
	}

	@Override
	public int getPageCount(String searchTerm) {
		long productCount = searchTerm != null && !searchTerm.isBlank()
				? productRepository.countByNameContainingIgnoreCase(searchTerm)
				: productRepository.count();
		return (int) Math.ceil((double) productCount / 10);
	}



}
