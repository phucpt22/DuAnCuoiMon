package com.poly.da2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping(value = {"/","/home/index"})
	public String home() {
		return "user/index";
	}
	
	@RequestMapping(value = {"/admin","/admin/home/index"})
	public String admin() {
		return "redirect:/assets/admin/index.html";
	}

}
