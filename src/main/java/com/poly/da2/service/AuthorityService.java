package com.poly.da2.service;


import com.poly.da2.entities.Authority;

import java.util.List;

public interface AuthorityService {

	public List<Authority> findAuthoritiesOfAdministrators();

	public List<Authority> findAll();
	
	public Authority create(Authority auth);
	
	public void delete(Integer id);
}
