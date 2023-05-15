package com.poly.da2.service.impl;

import com.poly.da2.repository.RoleRepository;
import com.poly.da2.entity.Role;
import com.poly.da2.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
    RoleRepository roleRepository;
	
	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

}
