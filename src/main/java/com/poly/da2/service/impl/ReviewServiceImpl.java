package com.poly.da2.service.impl;

import com.poly.da2.entity.Product;
import com.poly.da2.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewService reviewService;

    @Override
    public List<Product> finAll() {
        return reviewService.finAll();
    }

    @Override
    public Page<Product> listReviewByIdProduct(Integer idProduct, Pageable pageable) {
        return reviewService.listReviewByIdProduct(idProduct,pageable);
    }
}
