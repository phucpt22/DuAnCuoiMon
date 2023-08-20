package com.poly.da2.controller;


import com.poly.da2.entity.Notification;
import com.poly.da2.entity.Product;
import com.poly.da2.repository.ProductRepository;
import com.poly.da2.service.NotificationService;
import com.poly.da2.service.ProductService;
import com.poly.da2.service.OrderService;
import com.poly.da2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class HomeController {
	@Autowired
	ProductRepository dao;
	@Autowired
	OrderService os;
	@Autowired
	UserService us;
	@Autowired
	ProductService ps;
	@Autowired
	private NotificationService notificationService;

	@RequestMapping(value = {"/","/home/index"})
	public String home() {

		return "redirect:/products";
	}

	@RequestMapping(value = {"/admin","/admin/home/index"})
	public String admin(HttpSession request, Model model) {
		request.setAttribute("totalMoney",os.getTotalMoneyOrderToday());
		request.setAttribute("totalUsers",us.count());
		request.setAttribute("newUsers",us.countNewUsers());
		request.setAttribute("numberOfProducts",ps.count());
		List<Notification> notifications = notificationService.findAll();
		model.addAttribute("notifications",notifications);
		return "redirect:/assets/admin/index.html";
	}
	@Transactional(readOnly = true)
	@RequestMapping("/homepage")
	public String homepage(Model model) {
		List<Product> sanPhamMoiNhat = ps.sanPhamMoiNhat();
		List<Product> sanPhamMoiNhatCate1 = ps.sanPhamMoiNhatCate1();
		List<Product> sanPhamMoiNhatCate2 = ps.sanPhamMoiNhatCate2();
		List<Product> sanPhamMoiNhatCate6 = ps.sanPhamMoiNhatCate6();
		List<Product> bestseller = dao.sanphambanchay();
		model.addAttribute("spm", sanPhamMoiNhat);
		model.addAttribute("spmCate1", sanPhamMoiNhatCate1);
		model.addAttribute("spmCate2", sanPhamMoiNhatCate2);
		model.addAttribute("spmCate6", sanPhamMoiNhatCate6);
		model.addAttribute("bestseller", bestseller);
		return "index";
	}

}
