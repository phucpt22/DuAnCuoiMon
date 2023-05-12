package com.poly.da2.service.impl;

import com.poly.da2.model.User;
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
        return u.findAll();
    }

    @Override
    public List<User> getAdministrators() {
        return u.getAdministrators();
    }

    @Override
    public User findById(Integer id) {
        return u.findById(id).get();
    }

    @Override
    public User create(User user) {
        return u.save(user);
    }

    @Override
    public User update(User user) {
        return u.save(user);
    }

    @Override
    public void delete(Integer id) {
        u.deleteById(id);
    }
}
