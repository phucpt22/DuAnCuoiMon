package com.poly.da2.service;

import com.poly.da2.entity.Account;
import com.poly.da2.entity.NewUserEachMonth;
import com.poly.da2.entity.PasswordResetToken;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    Account findById(String id);

    Account create(Account account);

    Account update(Account account);

    void delete(String id);

    Account findbyEmail(String email);

    void resetPassword(Account user, String newPassword);

    List<Account> getAdministrators();
    List<NewUserEachMonth> getNewUserEachMonth(int year);
}
