package com.poly.da2.repository;

import com.poly.da2.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.category.id=?1")
	List<Product> getByCategoryId(String cid);
	@Query("SELECT o FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
	List<Product> findByPrice(double min, double max);
	@Query( "SELECT o FROM Product o WHERE o.name LIKE %?1%")
	List<Product> findSanPhamByName(String keywords);

}
