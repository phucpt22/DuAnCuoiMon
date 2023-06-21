package com.poly.da2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@RequestMapping(value = {"/","/home/index"})
	public String home() {

		return "redirect:/products";
	}

	@RequestMapping(value = {"/admin","/admin/home/index"})
	public String admin() {

		return "redirect:/assets/admin-ctrl/index.html";
	}
	@RequestMapping(value = {"/homepage"})
	public String aa() {
		return "user/index";
	}

}
