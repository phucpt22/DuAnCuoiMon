package com.poly.da2.rest;

import com.poly.da2.entities.Product;
import com.poly.da2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
	public Product getOne(@PathVariable("id")Integer id) {
		return productService.findById(id);
	}
//	@GetMapping("/find/{name}")
//	public List <Product> getName(@PathVariable("name")String name) {
//		return productService.findByName(name);
//	}

	@GetMapping("/find/{name}")
	public ResponseEntity<List<Product>> findProductsByName(@PathVariable String name) {
		List<Product> products = productService.findByName(name);
		if (products == null || products.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	@PostMapping
	public Product creat(@RequestBody Product product ) {
		return productService.create(product);
	}
	
	@PutMapping("{id}")
	public Product update(@PathVariable("id")Integer id,@RequestBody Product product ) {
		return productService.update(product); 
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id")Integer id) {
		productService.delete(id); 
	}
}
