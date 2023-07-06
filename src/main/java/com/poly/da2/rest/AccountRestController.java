package com.poly.da2.rest;

import com.poly.da2.entity.Account;
import com.poly.da2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/tk")
public class AccountRestController {
    @Autowired
    AccountService accountService;

    @GetMapping("/all")
    public List<Account> getAll() {
        return accountService.findAll();
    }


}
