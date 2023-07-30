package com.poly.da2.service;

import com.poly.da2.entity.Product;
import com.poly.da2.entity.TopProduct;
import com.poly.da2.model.ProductPageOutPut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ProductService {

	List<Product> findAll();
	Page<Product> findAll(Pageable pageable);

	Product findById(Integer id);

	Product create(Product product);

	Product update(Product product);

	void delete(Integer id);

	List<Product> sanPhamLienQuan(String cid, Pageable pageable);

	ProductPageOutPut filterProducts(String name, String cid, Double min_price, Double max_price, Pageable pageable);

	List<Product> sanPhamMoiNhat();
	List<Product> sanPhamMoiNhatCate1();
	List<Product> sanPhamMoiNhatCate2();
	List<Product> sanPhamMoiNhatCate6();

	long count();

	List<TopProduct> getTopProduct(Date from, Date to);

}
