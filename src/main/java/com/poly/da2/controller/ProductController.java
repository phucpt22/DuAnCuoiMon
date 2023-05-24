package com.poly.da2.controller;

import com.poly.da2.repository.ProductRepository;
import com.poly.da2.entity.Category;
import com.poly.da2.entity.Product;
import com.poly.da2.service.CategoryService;
import com.poly.da2.service.ParamService;
import com.poly.da2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ParamService paramService;
	@Autowired
    ProductRepository dao;

	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Product item = productService.findById(id);
		List<Product> list = productService.findAll();
		model.addAttribute("item", item);
		model.addAttribute("items", list);
		return "product/detail";
	}
	@PostMapping("/search")
	public String searchProducts(@RequestParam("searchTerm") String searchTerm,
								 @RequestParam(defaultValue = "0") int page,
								 @RequestParam(defaultValue = "6") int size,
								 Model model) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> productPage = productService.searchProducts(searchTerm, pageable);
		model.addAttribute("searchTerm", searchTerm);
		model.addAttribute("items", productPage.getContent());
		model.addAttribute("totalPages", productPage.getTotalPages());
		model.addAttribute("currentPage", page);
		return "product/store";
	}

	@GetMapping("/products")
	public String getProducts(@RequestParam(defaultValue = "0") int page,
							  @RequestParam(defaultValue = "6") int size,
							  @RequestParam("cid")Optional<String> cid,
							  Model model) {
		Pageable pageable = PageRequest.of(page, size);
		List<Category> listc = categoryService.findAll();
		Page<Product> productPage;
		if (cid.isPresent()) {
			productPage = productService.findByCategoryId(cid.get(), pageable);
		}else {
			productPage = productService.findAll(pageable);
		}
		model.addAttribute("items", productPage.getContent());
		model.addAttribute("cates", listc);
		model.addAttribute("totalPages", productPage.getTotalPages());
		model.addAttribute("currentPage", page);
		return "product/store";
	}


}
