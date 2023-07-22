package com.poly.da2.service.impl;

import com.poly.da2.repository.AuthorityRepository;
import com.poly.da2.repository.UserRepository;
import com.poly.da2.entity.Authority;
import com.poly.da2.entity.Userss;
import com.poly.da2.service.AccountService;
import com.poly.da2.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
    AuthorityRepository authorityRepository;
	@Autowired
	AccountService accountService;
	@Autowired
	UserRepository userdao;

	@Override
	public List<Authority> findAuthoritiesOfAdministrators() {
//		List<Account> accounts = accountService.getAdministrators();
//		return authorityDAO.authoritiesOf(accounts);
//		List<Account> accounts = acdao.getAdministrators();
//		return authorityRepository.authoritiesOf(accounts);
		List<Userss> users = userdao.getAdministrators();
		return authorityRepository.authoritiesOf(users);
	}

	@Override
	public List<Authority> findAll() {
		return authorityRepository.findAll();
	}

	@Override
	public Authority create(Authority auth) {
		return authorityRepository.save(auth);
	}

	@Override
	public void delete(Integer id) {
		authorityRepository.deleteById(id);
	}

}
