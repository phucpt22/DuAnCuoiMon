package com.poly.da2.repository;

import com.poly.da2.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("select a from Account a where a.username =?1")
    Account findAcc(String user);
}
