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


	@PostMapping("/product/find={name}")
	public String search(Model model, @PathVariable("name") String s) {
		String search = paramService.getString("name", "");
		List<Product> searchsp = dao.findSanPhamByName(s);
		model.addAttribute("searchsp", search);
		model.addAttribute("items", searchsp);
		return "product/list";
	}

	@RequestMapping("/product/filter")
	public String filter(Model model, @RequestParam("price") Double price) {
		List<Product> list;
		list = productService.findAll();
		try {
			List<Product> sr_price = null;
			if (price == 0) {
				model.addAttribute("price", price);
				sr_price = productService.findByPrice(0, 600000000);
			}else if(price == 1) {
				model.addAttribute("price", price);
				sr_price = productService.findByPrice(0,30);
			}
			else if (price == 2) {
				model.addAttribute("price", price);
				sr_price = productService.findByPrice(30, 49);
			} else if (price == 3) {
				model.addAttribute("price", price);
				sr_price = productService.findByPrice(50, 69);
			} else if (price == 4) {
				model.addAttribute("price", price);
				sr_price = productService.findByPrice(70, 89);
			} else if (price == 5) {
				model.addAttribute("price", price);
				sr_price = productService.findByPrice(90, 10000);
			}
			model.addAttribute("items", list);
			model.addAttribute("items", sr_price);
		}catch (Exception e) {
			e.printStackTrace();
		}

		return "prodcut/store";
	}
}
