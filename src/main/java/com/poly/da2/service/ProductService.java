package com.poly.da2.service;

import com.poly.da2.entity.Product;
import com.poly.da2.entity.TopProduct;
import com.poly.da2.model.ProductPageOutPut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

	List<Product> findAll();
	Page<Product> findAll(Pageable pageable);

	Product findById(Integer id);

	Page<Product> findByCategoryId(String cid, Pageable pageable);

	Product create(Product product);

	Product update(Product product);

	void delete(Integer id);

	Page<Product> searchProducts(String name, Pageable pageable);
	List<Product> sanPhamLienQuan(String cid, Pageable pageable);


	ProductPageOutPut filterProducts(String name, String cid, Double min_price, Double max_price, Pageable pageable);

	long count();

	List<TopProduct> getTopProduct();

}
