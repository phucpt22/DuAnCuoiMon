package com.poly.da2.controller;

import com.poly.da2.dao.ProductDAO;
import com.poly.da2.entities.Category;
import com.poly.da2.entities.Product;
import com.poly.da2.service.CategoryService;
import com.poly.da2.service.ParamService;
import com.poly.da2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	ProductDAO dao;

	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Product item = productService.findById(id);
		List<Product> list = productService.findAll();
		model.addAttribute("item", item);
		model.addAttribute("items", list);
		return "product/detail";
	}
	
	@RequestMapping("/product/list")
	public String list(Model model, @RequestParam("cid")Optional<String> cid) {
		List<Product> list;
		List<Category> listc;

		if (cid.isPresent()) {
			list= productService.findByCategoryId(cid.get());
		}else {
			list = productService.findAll();
		}
		listc = categoryService.findAll();
			model.addAttribute("items", list);
			model.addAttribute("cates", listc);
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
