package com.poly.da2.controller;

import com.poly.da2.entity.Reviews;
import com.poly.da2.repository.ProductRepository;
import com.poly.da2.entity.Category;
import com.poly.da2.entity.Product;
import com.poly.da2.repository.ReviewRepository;
import com.poly.da2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ParamService paramService;
	@Autowired
    ProductRepository dao;
	@Autowired
	ReviewRepository reviewRepository;

	@RequestMapping("/product/detail/{id}")
	public String detail(Model model,
						 @RequestParam(defaultValue = "0") int page,
						 @RequestParam(defaultValue = "6") int size,
						 @PathVariable("id") Integer id) {
		Pageable pageable = PageRequest.of(page, size);
		Product item = productService.findById(id);
		Page<Reviews> reviews = reviewRepository.listReviewByIdProduct(id,pageable);
		String imgs = item.getImage_urls();
		String[] strings = imgs.split(",");
		model.addAttribute("images", strings);
		model.addAttribute("reviews",reviews);
		model.addAttribute("item", item);
		return "product/detail";
	}
	@PostMapping("/search")
	public String searchProducts(@RequestParam("searchTerm") String searchTerm,
								 @RequestParam(defaultValue = "0") int page,
								 @RequestParam(defaultValue = "4") int size,
								 Model model) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> productPage = productService.searchProducts(searchTerm, pageable);
		model.addAttribute("searchTerm", searchTerm);
		model.addAttribute("items", productPage.getContent());
		model.addAttribute("totalPages", productPage.getTotalPages());
		model.addAttribute("currentPage", page);
		return "product/store";
	}
	@Transactional(readOnly = true)
	@GetMapping("/products")
	public String getProducts(@RequestParam(defaultValue = "0") int page,
							  @RequestParam(defaultValue = "6") int size,
							  @RequestParam(name = "cid", required = false)String cid,
							  @RequestParam(name = "searchTerm", required = false) String searchTerm,
							  Model model) {
		Pageable pageable = PageRequest.of(page, size);
		List<Category> listc = categoryService.findAll();
		List<Product> bestseller = dao.sanphambanchay();
		Page<Product> productPage = productService.filterProducts(searchTerm, cid, pageable);
		model.addAttribute("items", productPage.getContent());
		model.addAttribute("cates", listc);
		model.addAttribute("bestseller", bestseller);
		model.addAttribute("totalPages", productPage.getTotalPages());
		model.addAttribute("currentPage", page);
		return "product/store";
	}
	@PostMapping("/reviews")
	public String postReview(Reviews reviews){
		String nameReviewer = paramService.getString("nameReviewer", "");
		String emailReviewer = paramService.getString("emailReviewer", "");
		String contents = paramService.getString("contents", "");
		reviews.setNameReviewer(nameReviewer);
		reviews.setEmailReviewer(emailReviewer);
		reviews.setContents(contents);

		return "product/store";
	}

}
