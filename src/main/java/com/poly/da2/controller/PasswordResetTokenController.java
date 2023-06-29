package com.poly.da2.controller;

import com.poly.da2.entity.Account;
import com.poly.da2.entity.PasswordResetToken;
import com.poly.da2.repository.AccountRepository;
import com.poly.da2.repository.PasswordResetTokenRepository;
import com.poly.da2.service.AccountService;
import com.poly.da2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class PasswordResetTokenController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private AccountRepository accountRepository;


    @GetMapping("/forgot-password")
    public String form(){
        return "security/forgot-password";
    }
    @PostMapping("/forgot-password")
    public String submitForgotPasswordForm(@RequestParam("email") String email, Account account, Model model) {
        Account acc = accountRepository.findByEmail(email);
        System.out.println(acc);

        // Kiểm tra nếu email không tồn tại trong hệ thống
        if (acc == null) {
            // Xử lý thông báo lỗi hoặc chuyển hướng đến trang lỗi
            model.addAttribute("messerror","Không tìm thấy email");
            return "security/forgot-password";
        }

        // Tạo mã token ngẫu nhiên
        String token = generateToken();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUsername(acc);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(PasswordResetToken.EXPIRATION));
        passwordResetTokenRepository.save(passwordResetToken);

        // Gửi mã token qua email
        String subject = "Reset your password";
        String text = "Please click the link below to reset your password: "
                + "http://localhost:8080/reset-password?token=" + token;
        emailService.sendEmail(email, subject, text);
        model.addAttribute("token",token);
        // Chuyển hướng đến trang xác nhận đã gửi mã token thành công
        return "security/reset-password";
    }
    @GetMapping("/reset-password")
    public String form2(){
        return "security/reset-password";
    }
    private String generateToken() {
        // Độ dài của mã token
        int tokenLength = 20;

        // Chuỗi chứa các ký tự cho mã token
        String tokenChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Tạo ngẫu nhiên một chuỗi dấu mở ngoặc với độ dài bằng với tokenLength
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder tokenBuilder = new StringBuilder(tokenLength);
        for (int i = 0; i < tokenLength; i++) {
            int randomIndex = secureRandom.nextInt(tokenChars.length());
            char randomChar = tokenChars.charAt(randomIndex);
            tokenBuilder.append(randomChar);
        }

        // Mã hóa chuỗi token bằng Base64 để tạo mã token dạng chuỗi an toàn
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBuilder.toString().getBytes());

        return token;
    }

    @PostMapping("/reset-password")
    public String submitResetPasswordForm(@RequestParam("token") String token,
                                          @RequestParam("password") String password,
                                          @RequestParam("confirmPassword") String confirmPassword,
                                          Model model) {
        // Kiểm tra mã token trong cơ sở dữ liệu hoặc bất kỳ nơi nào phù hợp
        // Kiểm tra trùng khớp mật khẩu xác nhận
        if (!password.equals(confirmPassword) ) {
            // Xử lý thông báo lỗi hoặc chuyển hướng đến trang lỗi
            model.addAttribute("messerror","Password không trùng nhau");
            return "security/reset-password";
        }else{
            // Lấy thông tin người dùng dựa trên mã token
            //Account user = getUserDetailsFromToken(token);
            PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
            if (passwordResetToken != null && passwordResetToken.isExpired()) {
                // Tìm thông tin người dùng dựa trên id người dùng từ mã token
                Account user = accountRepository.findByEmail(passwordResetToken.getUsername().getGmail());
                if (user != null) {
                    user.setPassword(confirmPassword);
                    accountRepository.save(user);
                    model.addAttribute("message","Đổi mật khẩu thành công");
                }
            }
        }
        //String encodedPassword = passwordEncoder.encode(password);
        //updateUserPassword(user.getGmail(), encodedPassword);
        // Chuyển hướng đến trang xác nhận đã đổi mật khẩu thành công
        return "security/login";
    }

//    private Account getUserDetailsFromToken(String token) {
//        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
//        if (passwordResetToken != null && passwordResetToken.isExpired()) {
//            // Tìm thông tin người dùng dựa trên id người dùng từ mã token
//            Account user = accountRepository.findByEmail(passwordResetToken.getUsername().getGmail());
//            if (user != null) {
//                return new Account(user.getGmail(), user.getPassword());
//            }
//        }
//        return null;
//    }
    private void updateUserPassword(String gmail, String encodedPassword) {
        Account user = accountRepository.findByEmail(gmail);
        if (user != null) {
            user.setPassword(encodedPassword);
            accountRepository.save(user);
        }
    }
}
