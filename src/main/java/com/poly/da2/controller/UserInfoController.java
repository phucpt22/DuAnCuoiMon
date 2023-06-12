package com.poly.da2.controller;

import com.poly.da2.entity.Userss;
import com.poly.da2.service.AccountService;
import com.poly.da2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userinfo")
public class UserInfoController {
    @Autowired
    AccountService aService;
    @GetMapping
    public String userinfo() {
        return "user/userinfo";
    }
    @GetMapping("/userinfo-login")
    public Userss getOneByUserLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return aService.findById(auth.getPrincipal());
    }
}
