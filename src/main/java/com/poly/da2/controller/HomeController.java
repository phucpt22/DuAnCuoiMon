package com.poly.da2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	@RequestMapping(value = {"/","/home/index"})
	public String home() {
		return "redirect:/product/list";
	}

	@RequestMapping(value = {"/abc"})
	public String dsds(@RequestParam String x) {
		System.out.println(x);
		return null;
	}
	@RequestMapping(value = {"/admin","/admin/home/index"})
	public String admin() {
		return "redirect:/assert/admin-ctrl/index.html";
	}
	@RequestMapping(value = {"/homepage"})
	public String aa() {
		return "user/index";
	}

}
