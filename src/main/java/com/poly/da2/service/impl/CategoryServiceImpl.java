package com.poly.da2.service.impl;

import com.poly.da2.repository.CategoryRepository;
import com.poly.da2.entity.Category;
import com.poly.da2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
    CategoryRepository categoryRepository;

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findById(String id) {
		return categoryRepository.findById(id).get();
	}

	@Override
	public Category create(Category category) {
		// TODO Auto-generated method stub
		return categoryRepository.save(category);
	}

	@Override
	public Category update(Category category) {
		// TODO Auto-generated method stub
		return categoryRepository.save(category);
	}

	@Override
	public void delete(String id) {
		categoryRepository.deleteById(id);
	}
}
