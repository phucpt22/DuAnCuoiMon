package com.poly.da2.repository;

import com.poly.da2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.category.id=?1")
	List<Product> getByCategoryId(String cid);
	@Query("SELECT o FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
	List<Product> findByPrice(double min, double max);
	@Query( "SELECT o FROM Product o WHERE o.name LIKE %?1%")
	List<Product> findSanPhamByName(String keywords);

}
