package com.poly.da2.repository;

import com.poly.da2.entity.Product;
import com.poly.da2.entity.TopProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

	@Query(value = "SELECT TOP 6 * FROM products ORDER BY createdate DESC", nativeQuery = true)
	List<Product> sanPhamMoiNhat();
	@Query(value = "SELECT TOP 3 * FROM products WHERE categoryid like 'cate1' ORDER BY createdate DESC", nativeQuery = true)
	List<Product> sanPhamMoiCate1();
	@Query(value = "SELECT TOP 3 * FROM products WHERE categoryid like 'cate2' ORDER BY createdate DESC", nativeQuery = true)
	List<Product> sanPhamMoiCate2();
	@Query(value = "SELECT TOP 3 * FROM products WHERE categoryid like 'cate6' ORDER BY createdate DESC", nativeQuery = true)
	List<Product> sanPhamMoiCate6();

	@Query(value = "SELECT COUNT(*) FROM Product p")
	long count();


	@Query("SELECT new TopProduct (p.id, p.name, p.image_urls, SUM(od.quantity),SUM( od.quantity * od.price))\n" +
			"from Product p\n" +
			"\tinner join OrderDetail od\n" +
			"\ton od.product.id = p.id\n" +
			"\tinner join Order o\n" +
			"\ton o.id = od.order.id\n" +
			"\t where o.createDate BETWEEN  ?1  AND ?2 \n" +
			"\tgroup by p.id, p.name, p.image_urls\n" +
			"\torder by SUM( od.quantity * od.price) desc\n")
	List<TopProduct> getTopProduct(Date from, Date to);

}
