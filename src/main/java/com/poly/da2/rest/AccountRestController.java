package com.poly.da2.rest;

import com.poly.da2.entity.Account;
import com.poly.da2.entity.NewUserEachMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.poly.da2.service.AccountService;

import com.poly.da2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/accounts")
public class AccountRestController {
	@Autowired
	AccountService accountService;

    @GetMapping("/all")
    public List<Account> getAll(){
        return accountService.findAll();
    }
	@GetMapping
	public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin) {
		if (admin.orElse(false)) {
			return accountService.getAdministrators();
		}
		return accountService.findAll();
	}
//
//	@GetMapping("/all")
//	public List<Account> getAll() {
//		return accountService.findAll();
//	}
//
//	@GetMapping("{id}")
//	public Account getOne(@PathVariable("id" )String id) {
//		return accountService.findById(id);
//	}
//
//	@PostMapping
//	public Account creat(@RequestBody Account account ) {
//		return accountService.create(account);
//	}
//
//	@PutMapping("{id}")
//	public Account update(@PathVariable("id")String id,@RequestBody Account account ) {
//		return accountService.update(account);
//	}
//
//	@DeleteMapping("{id}")
//	public void delete(@PathVariable("id")String id) {
//		accountService.delete(id);
//	}

//@RequestMapping("/rest/tk")
//public class AccountRestController {
//    @Autowired
//    AccountService accountService;
//
//    @GetMapping("/all")
//    public List<Account> getAll() {
//        return accountService.findAll();
//    }



    @GetMapping("new-user-each-month{year}")
    public List<NewUserEachMonth> getNewUserEachMonth(@PathVariable int year) {
        return accountService.getNewUserEachMonth(year);
    }
}
