package com.poly.da2.service.impl;

import com.poly.da2.dao.RoleDAO;
import com.poly.da2.entities.Role;
import com.poly.da2.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	RoleDAO roleDAO;
	
	@Override
	public List<Role> findAll() {
		return roleDAO.findAll();
	}

}
