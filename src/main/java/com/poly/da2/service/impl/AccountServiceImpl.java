package com.poly.da2.service.impl;

import com.poly.da2.entity.NewUserEachMonth;
import com.poly.da2.repository.AccountRepository;
import com.poly.da2.entity.Account;

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
		return acc.findAll();
	}

	@Override
	public Account findById(String id) {
		return acc.findById(id).get();
	}

	@Override
	public Account create(Account account) {
		return acc.save(account);
	}

	@Override
	public Account update(Account account) {
		return acc.save(account);
	}

	@Override
	public void delete(String id) {
		acc.deleteById(id);
	}

	@Override
	public Account findbyEmail(String email) {
		return acc.findByEmail(email);
	}


    @Override
    public List<Account> getAdministrators() {

		return acc.getAdministrators();
    }

    @Override
	public void resetPassword(Account user, String newPassword) {
		// Đặt lại mật khẩu mới cho người dùng
		user.setPassword(newPassword);
		// Lưu người dùng đã được cập nhật
		acc.save(user);
	}


	@Override
	public List<NewUserEachMonth> getNewUserEachMonth(int year) {
		return acc.getNewUserEachMonth(year);
	}

}
