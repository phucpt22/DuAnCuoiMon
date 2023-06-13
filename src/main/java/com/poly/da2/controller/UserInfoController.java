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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/userinfo")
public class UserInfoController {
    @Autowired
    UserService uService;
    @Autowired
    AccountService aService;
    @GetMapping
    public String userinfo() {
        return "user/userinfo";
    }
    @GetMapping("/userinfo-login")
    @ResponseBody
    public Userss getOneByUserLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal());
        int userId = aService.findById("tungle2722002@gmail.com").getUser().getId();
        return uService.findById(userId);
    }
}
