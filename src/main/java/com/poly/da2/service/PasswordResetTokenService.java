package com.poly.da2.service;

import com.poly.da2.entity.Account;
import com.poly.da2.entity.PasswordResetToken;
import com.poly.da2.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetTokenService {
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    public PasswordResetToken createToken(Account user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUsername(user);
        passwordResetToken.setExpiryDate(calculateExpiryDate());

        return tokenRepository.save(passwordResetToken);
    }

    public boolean validateToken(String token) {
        PasswordResetToken passwordResetToken = tokenRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getExpiryDate().before(new Date())) {
            return false;
        }
        return true;
    }

    private Date calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, PasswordResetToken.EXPIRATION);
        return calendar.getTime();
    }
}
