package com.poly.da2.repository;

import com.poly.da2.entity.Userss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserRepository extends JpaRepository<Userss,Integer> {

    @Query("select distinct a.user from Authority a where a.role.id IN ('1','2')")
    List<Userss> getAdministrators();
    @Query("SELECT a FROM Userss a WHERE a.fullName = ?1")
    Userss findByFullname(String username);
    @Query("SELECT a FROM Userss a WHERE a.gmail = ?1")
    Userss findByEmail(String email);
}
