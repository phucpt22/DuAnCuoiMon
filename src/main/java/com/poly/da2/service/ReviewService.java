package com.poly.da2.service;

import com.poly.da2.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    List<Product> finAll();
    Page<Product> listReviewByIdProduct(Integer idProduct, Pageable pageable);
}
