package com.poly.da2.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.da2.entity.Userss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserRepository extends JpaRepository<Userss,Integer> {

    @Query("select distinct a.user from Authority a where a.role.id IN ('1','2')")
    List<Userss> getAdministrators();
    @Query("SELECT a FROM Userss a WHERE a.account.username = ?1")
    Userss findByUserName(String username);
    @Query("SELECT a FROM Userss a WHERE a.gmail = ?1")
    Userss findByEmail(String email);
    @Query("SELECT a FROM Userss a WHERE a.id = ?1")
    Userss findOneById(Integer id);
}
