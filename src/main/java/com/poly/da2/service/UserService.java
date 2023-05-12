package com.poly.da2.service;

import com.poly.da2.entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    List<User> getAdministrators();

    User findById(String id);

    User create(User user);

    User update(User user);

    void delete(String id);
}
