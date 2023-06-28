package com.poly.da2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "passwordResetToken")
public class PasswordResetToken {

    public static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "username")
    private Account username;

    private LocalDateTime expiryDate;

    public PasswordResetToken(String token, Account user) {
    }
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
