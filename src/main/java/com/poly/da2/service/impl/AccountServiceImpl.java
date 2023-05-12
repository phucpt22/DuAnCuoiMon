package com.poly.da2.service.impl;

import com.poly.da2.repository.AccountRepository;
import com.poly.da2.entities.Account;
import com.poly.da2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository acc;
	@Override
	public List<Account> findAll() {
		return null;
	}

	@Override
	public Account findById(String id) {
		return null;
	}

	@Override
	public Account create(Account account) {
		return null;
	}

	@Override
	public Account update(Account account) {
		return null;
	}

	@Override
	public void delete(String id) {

	}
}
