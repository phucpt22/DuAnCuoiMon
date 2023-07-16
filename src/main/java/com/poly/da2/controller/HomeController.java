package com.poly.da2.controller;


import com.poly.da2.entity.Product;
import com.poly.da2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
	@Autowired
	ProductService productService;

	@RequestMapping(value = {"/","/home/index"})
	public String home() {

		return "redirect:/products";
	}

	@RequestMapping(value = {"/admin","/admin/home/index"})
	public String admin() {

		return "redirect:/assets/admin/index.html";
	}
	@RequestMapping("/homepage")
	public String homepage(Model model) {
		List<Product> sanPhamMoiNhat = productService.sanPhamMoiNhat();
		List<Product> sanPhamMoiNhatCate1 = productService.sanPhamMoiNhatCate1();
		List<Product> sanPhamMoiNhatCate2 = productService.sanPhamMoiNhatCate2();
		List<Product> sanPhamMoiNhatCate6 = productService.sanPhamMoiNhatCate6();
		model.addAttribute("spm", sanPhamMoiNhat);
		model.addAttribute("spmCate1", sanPhamMoiNhatCate1);
		model.addAttribute("spmCate2", sanPhamMoiNhatCate2);
		model.addAttribute("spmCate6", sanPhamMoiNhatCate6);
		return "index";
	}

}
