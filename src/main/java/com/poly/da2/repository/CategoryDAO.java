package com.poly.da2.repository;


import com.poly.da2.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, String> {

}
