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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private AccountRepository accountRepository;


    @GetMapping("/forgot-password")
    public String form(){
        return "security/forgot-password";
    }
    @PostMapping("/forgot-password")
    public String submitForgotPasswordForm(@RequestParam("email") String email, HttpServletRequest httpServletRequest, Model model) {
        Account acc = accountRepository.findByEmail(email);
        if (acc == null) {
            model.addAttribute("messerror","Không tìm thấy email");
            return "security/forgot-password";
        }
        String token = generateToken();

        String subject = "Reset your password";
        String text = "Please click the link below to reset your password: "
                + "http://localhost:8080/reset-password/" + token;
        emailService.sendEmail(email, subject, text);
        httpServletRequest.getSession().setAttribute("token",token);
        httpServletRequest.getSession().setAttribute("email",email);
        model.addAttribute("mess","Gửi thành công, vui lòng kiểm tra mail");
        return "security/forgot-password";
    }
    @RequestMapping("/reset-password/{token}")
    public String form3(@PathVariable(name = "token") String token,Model model, HttpServletRequest httpServletRequest){
        String s = (String) httpServletRequest.getSession().getAttribute("token");
        if(s.equals(token)){
            model.addAttribute("mess","Xác nhận thành công");
            return "security/reset-password";
        }else {
            model.addAttribute("mess","Xác nhận không thành công");
            return "security/forgot-password";
        }
    }
    @PostMapping("/reset-password")
    public String submitResetPasswordForm(HttpServletRequest httpServletRequest,
                                          @RequestParam("password") String password,
                                          @RequestParam("confirmPassword") String confirmPassword,
                                          Model model) {
        if (!password.equals(confirmPassword) ) {
            model.addAttribute("messerror","Password không trùng nhau");
            return "security/reset-password";
        }else{
            String mail = (String) httpServletRequest.getSession().getAttribute("email");
            Account user = accountRepository.findByEmail(mail);
            if (user != null) {
                user.setPassword(confirmPassword);
                accountRepository.save(user);
                model.addAttribute("message","Đổi mật khẩu thành công");
            }
            return "security/login";
        }
    }
    private String generateToken() {
        int tokenLength = 20;
        String tokenChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder tokenBuilder = new StringBuilder(tokenLength);
        for (int i = 0; i < tokenLength; i++) {
            int randomIndex = secureRandom.nextInt(tokenChars.length());
            char randomChar = tokenChars.charAt(randomIndex);
            tokenBuilder.append(randomChar);
        }
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBuilder.toString().getBytes());
        return token;
    }

}
