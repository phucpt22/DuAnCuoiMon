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
	List<Product> filterProduct(@Param("name") String name, @Param("cid") String cid, @Param("min_price") Double min_price, @Param("max_price") Double max_price);

	@Transactional(readOnly = true)
	@Procedure(name="Product.sp_SpDuoc_Mua_nhieu")
	List<Product> sanphambanchay();

	@Query(value = "SELECT p FROM Product p WHERE p.category.id = ?1 ORDER BY NEWID()")
	List<Product> SanPhamLienQuan(String cid, Pageable pageable);

}
