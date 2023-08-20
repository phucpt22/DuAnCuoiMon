package com.poly.da2.service;

import com.nimbusds.oauth2.sdk.util.date.SimpleDate;
import com.poly.da2.entity.Category;
import com.poly.da2.entity.RevenueByCateGory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface CategoryService {

	List<Category> findAll();

	Category findById(String id);

	Category create(Category category);

	Category update(Category category);

	void delete(String id);

	List<RevenueByCateGory> getRevenueByCategory(Date from, Date to);

}
