package com.poly.da2.service.impl;

import com.poly.da2.entity.Userss;
import com.poly.da2.repository.UserRepository;
import com.poly.da2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository u;
    @Override
    public List<Userss> findAll() {
        return u.findAll();
    }

    @Override
    public List<Userss> getAdministrators() {
        return u.getAdministrators();
    }

    @Override
    public Userss findById(Integer id) {
        return u.findById(id).get();
    }

    @Override
    public Userss findByEmail(String email) {
        return u.findByEmail(email);
    }

    @Override
    public Userss create(Userss userss) {
        return u.save(userss);
    }

    @Override
    public Userss update(Userss userss) {
        return u.save(userss);
    }

    @Override
    public void delete(Integer id) {
        u.deleteById(id);
    }

    @Override
    public void updateUserImage(int id, String nameImage) throws ChangeSetPersister.NotFoundException {
        Userss user = u.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        user.setPhoto(nameImage);
        u.save(user);
    }

    @Override
    public long count() {
        return u.count();
    }

    @Override
    public long countNewUsers() {
        return u.countNewUsers();
    }

}
