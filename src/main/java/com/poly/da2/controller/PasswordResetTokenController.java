package com.poly.da2.controller;

import com.poly.da2.entity.Account;
import com.poly.da2.entity.PasswordResetToken;
import com.poly.da2.service.AccountService;
import com.poly.da2.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PasswordResetTokenController {
    @Autowired
    private AccountService accService;

    @Autowired
    private PasswordResetTokenService tokenService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPasswordForm(@RequestParam("email") String email, HttpServletRequest request) {
        Account user = accService.findbyEmail(email);
        if (user != null) {
            PasswordResetToken token = tokenService.createToken(user);
            String resetUrl = getResetUrl(request, token.getToken());
            // Gửi email chứa đường dẫn đặt lại mật khẩu đến người dùng
        }
        return "redirect:/forgot-password?success";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        if (tokenService.validateToken(token)) {
            model.addAttribute("token", token);
            return "reset-password";
        }
        return "redirect:/forgot-password?error";
    }

    @PostMapping("/reset-password")
    public String processResetPasswordForm(@RequestParam("token") String token, @RequestParam("password") String password) {
        if (tokenService.validateToken(token)) {
            // Cập nhật mật khẩu mới cho người dùng
            return "redirect:/login?resetSuccess";
        }
        return "redirect:/forgot-password?error";
    }

    private String getResetUrl(HttpServletRequest request, String token) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return baseUrl + "/reset-password?token=" + token;
    }
}
