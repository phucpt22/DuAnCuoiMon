package com.poly.da2.service;

import com.poly.da2.entity.Userss;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserService {
    List<Userss> findAll();

    List<Userss> getAdministrators();

    Userss findById(Integer id);

    Userss findByEmail(String email);

    Userss create(Userss userss);

    Userss update(Userss userss);

    void delete(Integer id);

    void updateUserImage(int id, String nameImage) throws ChangeSetPersister.NotFoundException;

}
