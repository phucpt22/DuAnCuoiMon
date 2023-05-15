package com.poly.da2.service;

import com.poly.da2.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

	List<Category> findAll();

	Category findById(String id);

	Category create(Category category);

	Category update(Category category);

	void delete(String id);

}
