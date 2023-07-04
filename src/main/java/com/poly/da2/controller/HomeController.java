package com.poly.da2.controller;


import com.poly.da2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	OrderService os;

	@RequestMapping(value = {"/","/home/index"})
	public String home() {

		return "redirect:/products";
	}

	@RequestMapping(value = {"/admin","/admin/home/index"})
	public String admin(HttpSession request) {
		request.setAttribute("totalMoney",os.getTotalMoneyOrderToday());
		System.out.println(os.getTotalMoneyOrderToday());
		return "redirect:/assets/admin/index.html";
	}
	@RequestMapping(value = {"/homepage"})
	public String aa() {
		return "user/index";
	}

}
