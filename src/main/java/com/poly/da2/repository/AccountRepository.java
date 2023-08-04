package com.poly.da2.repository;

import com.poly.da2.entity.Account;
import com.poly.da2.entity.NewUserEachMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.w3c.dom.stylesheets.LinkStyle;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("select a from Account a where a.username =?1")
    Account findAcc(String user);

    @Query("select a from Account a")
    List<Account> findAll();

    @Query("select a from Account a where a.gmail =?1")
    Account findByEmail(String email);

    @Query("select distinct a.user from Authority a where a.role.id IN ('r1','r2')")
    List<Account> getAdministrators();

    @Query("SELECT new NewUserEachMonth (MONTH(a.createDate), COUNT(*) )\n" +
            "FROM Account a\n" +
            "where YEAR(a.createDate) = ?1 \n" +
            "GROUP BY  MONTH(a.createDate)\n" +
            "ORDER BY  MONTH(a.createDate)")
    List<NewUserEachMonth> getNewUserEachMonth(int year);
}
