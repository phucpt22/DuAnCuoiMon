package com.poly.da2.service.impl;

import com.poly.da2.entities.User;
import com.poly.da2.repository.CategoryRepository;
import com.poly.da2.repository.UserRepository;
import com.poly.da2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository u;
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> getAdministrators() {
        return null;
    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
