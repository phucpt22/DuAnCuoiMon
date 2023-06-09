package com.poly.da2.repository;

import com.poly.da2.entity.Reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Reviews, String> {
    @Query("SELECT r FROM reviews r where r.product_id.id = ?1")
    Page<Reviews> listReviewByIdProduct(Integer idProduct, Pageable pageable);
}
