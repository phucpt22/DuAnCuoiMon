package com.poly.da2.repository;

import com.poly.da2.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.category.id=?1")
	Page<Product> getByCategoryId(String cid, Pageable pageable);
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
	Page<Product> getByName(String name, Pageable pageable);
	@Query("SELECT o FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
	List<Product> findByPrice(double min, double max);
	@Query( "SELECT o FROM Product o WHERE o.name LIKE %?1%")
	List<Product> findSanPhamByName(String keywords);
	Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
	long countByNameContainingIgnoreCase(String searchTerm);
}
