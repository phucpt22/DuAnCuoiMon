package com.poly.da2.controller;

import com.poly.da2.repository.AccountRepository;
import com.poly.da2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class OrderController {
	@Autowired
	OrderService orderService;
	@Autowired
	AccountRepository accRepository;

	@RequestMapping("/order/checkout")
	public String checkout(Model model, Principal principal) {
		String username = principal.getName(); // Lấy tên đăng nhập
		Integer userId = accRepository.findAcc(username).getUser().getId(); // Lấy ID của user
		model.addAttribute("userId", userId); // Truyền ID của user qua Model
		return "cart/checkout";
	}

	@RequestMapping("/order/list")
	public String list(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		model.addAttribute("orders", orderService.findByUsername(username));
		return "order/list";
	}

	@RequestMapping("/order/detail/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("order", orderService.findById(id));
		return "order/detail";
	}
}
