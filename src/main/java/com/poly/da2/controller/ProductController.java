package com.poly.da2.controller;

import com.poly.da2.entity.Reviews;
import com.poly.da2.entity.Userss;
import com.poly.da2.model.ProductPageOutPut;
import com.poly.da2.repository.ProductRepository;
import com.poly.da2.entity.Category;
import com.poly.da2.entity.Product;
import com.poly.da2.repository.ReviewRepository;
import com.poly.da2.repository.UserRepository;
import com.poly.da2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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

	@Autowired
	UserRepository uRepository;
	@RequestMapping("/product/detail/{id}")
	public String detail(Model model,
						 @RequestParam(defaultValue = "0") int page,
						 @RequestParam(defaultValue = "4") int size,
						 @RequestParam String idcate,
						 @PathVariable("id") Integer id, HttpServletRequest request) {
		Pageable pageable = PageRequest.of(page, size);
		Product item = productService.findById(id);
		Page<Reviews> reviews = reviewRepository.listReviewByIdProduct(id,pageable);

		List<Product> latedList = productService.sanPhamLienQuan(idcate,pageable);

		String imgs = item.getImage_urls();
		String[] strings = imgs.split(",");
		model.addAttribute("latedList",latedList);
		model.addAttribute("images", strings);
		model.addAttribute("reviews",reviews);
		model.addAttribute("item", item);
		model.addAttribute("totalPages", reviews.getTotalPages());
		model.addAttribute("currentPage", page);
		return "product/detail";
	}
//	@PostMapping("/search")
//	public String searchProducts(@RequestParam("searchTerm") String searchTerm,
//								 @RequestParam(defaultValue = "0") int page,
//								 @RequestParam(defaultValue = "4") int size,
//								 Model model) {
//		Pageable pageable = PageRequest.of(page, size);
//		Page<Product> productPage = productService.searchProducts(searchTerm, pageable);
//		model.addAttribute("searchTerm", searchTerm);
//		model.addAttribute("items", productPage.getContent());
//		model.addAttribute("totalPages", productPage.getTotalPages());
//		model.addAttribute("currentPage", page);
//		return "product/store";
//	}
	@Transactional(readOnly = true)
	@GetMapping("/products")
	public String getProducts(@RequestParam(defaultValue = "0") int page,
							  @RequestParam(defaultValue = "6") int size,
							  @RequestParam(name = "cid", defaultValue = "")String cid,
							  @RequestParam(name = "name", defaultValue = "") String name,
							  Model model) {
		Pageable pageable = PageRequest.of(page, size);
		List<Category> listc = categoryService.findAll();
		List<Product> bestseller = dao.sanphambanchay();
		ProductPageOutPut productPage = productService.filterProducts( name, cid, pageable);
		model.addAttribute("items", productPage.getProducts());
		model.addAttribute("cates", listc);
		model.addAttribute("bestseller", bestseller);
		model.addAttribute("totalPages", productPage.getTotalPage());
		model.addAttribute("currentPage", page);
		model.addAttribute("cid", cid);
		model.addAttribute("name", name);
		return "product/store";
	}
	@PostMapping("/reviews")
	public String postReview(HttpServletRequest request, Reviews reviews, Principal principal){
		String nameReviewer = paramService.getString("nameReviewer", "");
		String emailReviewer = paramService.getString("emailReviewer", "");
		String contents = paramService.getString("contents", "");
		Integer idProduct = Integer.valueOf(request.getParameter("idProduct"));
		try {
			reviews.setName_reviewer(nameReviewer);
			reviews.setEmail_reviewer(emailReviewer);
			reviews.setContents(contents);
			double quanityStar = 0;
			if (request.getParameter("rating5") != null) {
				quanityStar = 5;
			} else if (request.getParameter("rating4") != null) {
				quanityStar = 4;
			} else if (request.getParameter("rating3") != null) {
				quanityStar = 3;
			} else if (request.getParameter("rating2") != null) {
				quanityStar = 2;
			} else if (request.getParameter("rating1") != null) {
				quanityStar = 1;
			}
			reviews.setQuanity_star(quanityStar);

			String user = principal.getName();
			Userss userss = uRepository.findByUserName(user);
			reviews.setUser_id(userss);

			Product product = dao.getById(idProduct);
			reviews.setProduct_id(product);

			reviewRepository.save(reviews);
		}catch (Exception e){
			e.getStackTrace();
		}
		return "redirect:/product/detail/" + idProduct;
	}

}
