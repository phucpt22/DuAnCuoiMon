package com.poly.da2.service;


import com.poly.da2.entities.Account;

import java.util.List;

public interface AccountService {

	List<Account> findAll();

	Account findById(String id);

	Account create(Account account);

	Account update(Account account);

	void delete(String id);

}
