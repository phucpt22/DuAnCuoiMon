package com.poly.da2.service.impl;

import com.nimbusds.oauth2.sdk.util.date.SimpleDate;
import com.poly.da2.entity.Category;
import com.poly.da2.entity.RevenueByCateGory;
import com.poly.da2.repository.CategoryRepository;
import com.poly.da2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
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
        if (null == category.getId())
            System.out.println("Id not null");

        Category entity = categoryRepository.findById(category.getId()).orElse(null);
        entity.setName(category.getName());
        return categoryRepository.save(entity);
    }

    @Override
    public void delete(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<RevenueByCateGory> getRevenueByCategory(Date from, Date to) {
        return categoryRepository.getRevenueByCategory(from,to);
    }
}
