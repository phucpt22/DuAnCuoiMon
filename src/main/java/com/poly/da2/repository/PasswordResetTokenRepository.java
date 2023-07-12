package com.poly.da2.repository;

import com.poly.da2.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
    @Query("SELECT DISTINCT p FROM PasswordResetToken p WHERE p.token IN ?1")
    PasswordResetToken findByToken(String token);
}
