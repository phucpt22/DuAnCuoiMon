package com.poly.da2.rest;

import com.nimbusds.oauth2.sdk.util.date.SimpleDate;
import com.poly.da2.entity.Category;
import com.poly.da2.entity.RevenueByCateGory;
import com.poly.da2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("{id}")
    public Category getOne(@PathVariable("id") String id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public Category creat(@RequestBody Category category) {
        return categoryService.create(category);
    }

    @PutMapping("{id}")
    public Category update(@PathVariable("id") String id, @RequestBody Category category) {
        return categoryService.update(category);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id) {
        categoryService.delete(id);
    }

    @GetMapping("revenue-by-category")
    public List<RevenueByCateGory> getRevenueByCategory(@RequestParam("from") String from,@RequestParam("to")  String to) throws ParseException {
       return  categoryService.getRevenueByCategory(
               new SimpleDateFormat("yyyy-MM-dd").parse(from),
               new SimpleDateFormat("yyyy-MM-dd").parse(to));
    }
}
