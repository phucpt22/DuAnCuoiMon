package com.poly.da2.rest;

import com.poly.da2.entity.Product;
import com.poly.da2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
    @Autowired
    ProductService productService;

    @GetMapping("")
    public List<Product> getAll() {
        return productService.findAll();
    }

    @GetMapping("{id}")
    public Product getOne(@PathVariable("id") Integer id) {
        return productService.findById(id);
    }

    @RequestMapping("/api/products")
    @ResponseBody
    public Page<Product> getProductsApi(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "9") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return productService.findAll(pageable);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping("{id}")
    public Product update(@PathVariable("id") Integer id, @RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        productService.delete(id);
    }

    @GetMapping("count")
    public long count(){
        return productService.count();
    }
}
