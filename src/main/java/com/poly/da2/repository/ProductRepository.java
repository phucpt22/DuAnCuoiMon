package com.poly.da2.repository;

import com.poly.da2.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.category.id=?1")
	Page<Product> getByCategoryId(String cid, Pageable pageable);
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
	Page<Product> getByName(String name, Pageable pageable);

	@Procedure(name="filterProduct")
	Page<Product> filterProduct(@Param("name") String name, @Param("cid") String cid, Pageable pageble);

	@Query("SELECT o FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
	Page<Product> findByPrice(double min, double max, Pageable pageable);
	@Query( "SELECT o FROM Product o WHERE o.name LIKE %?1%")
	long countByNameContainingIgnoreCase(String searchTerm);
	@Query( "SELECT o FROM Product o WHERE o.name LIKE %?1%")
	Product findByName(String name);
	@Transactional(readOnly = true)
	@Procedure(name="Product.sp_SpDuoc_Mua_nhieu")
	List<Product> sanphambanchay();

	@Query(value = "SELECT p FROM Product p WHERE p.category.id = ?1 ORDER BY NEWID()")
	List<Product> SanPhamLienQuan(String cid, Pageable pageable);

}
