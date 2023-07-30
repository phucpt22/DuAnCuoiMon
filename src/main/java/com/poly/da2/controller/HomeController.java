package com.poly.da2.controller;


import com.poly.da2.entity.Product;
import com.poly.da2.repository.ProductRepository;
import com.poly.da2.service.ProductService;

import com.poly.da2.service.OrderService;

import com.poly.da2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class HomeController {
	@Autowired
	ProductService productService;
	@Autowired
	ProductRepository dao;

	@Autowired
	OrderService os;

	@Autowired
	UserService us;




	@Autowired
	ProductService ps;

	@RequestMapping(value = {"/","/home/index"})
	public String home(@AuthenticationPrincipal OAuth2User principal) {
//		String email1 = principal.getAttribute("email");
//		System.out.println(email1);
		return "redirect:/products";
	}

	@RequestMapping(value = {"/admin","/admin/home/index"})
	public String admin(HttpSession request) {
		request.setAttribute("totalMoney",os.getTotalMoneyOrderToday());
		request.setAttribute("totalUsers",us.count());
		request.setAttribute("newUsers",us.countNewUsers());
		request.setAttribute("numberOfProducts",productService.count());

		return "redirect:/assets/admin/index.html";
	}
	@Transactional(readOnly = true)
	@RequestMapping("/homepage")
	public String homepage(Model model) {
		List<Product> sanPhamMoiNhat = productService.sanPhamMoiNhat();
		List<Product> sanPhamMoiNhatCate1 = productService.sanPhamMoiNhatCate1();
		List<Product> sanPhamMoiNhatCate2 = productService.sanPhamMoiNhatCate2();
		List<Product> sanPhamMoiNhatCate6 = productService.sanPhamMoiNhatCate6();
		List<Product> bestseller = dao.sanphambanchay();
		model.addAttribute("spm", sanPhamMoiNhat);
		model.addAttribute("spmCate1", sanPhamMoiNhatCate1);
		model.addAttribute("spmCate2", sanPhamMoiNhatCate2);
		model.addAttribute("spmCate6", sanPhamMoiNhatCate6);
		model.addAttribute("bestseller", bestseller);
		return "index";
	}

}
