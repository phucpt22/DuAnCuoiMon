package com.poly.da2.repository;

import com.poly.da2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select distinct a.user from Authority a where a.role.id IN ('1','2')")
    List<User> getAdministrators();
    @Query("SELECT a FROM User a WHERE a.fullName = ?1")
    User findByFullname(String username);
}
