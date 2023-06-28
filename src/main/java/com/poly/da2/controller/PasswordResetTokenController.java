package com.poly.da2.controller;

import com.poly.da2.entity.Account;
import com.poly.da2.entity.PasswordResetToken;
import com.poly.da2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetTokenController {
    @Autowired
    private AccountService userService;

    @GetMapping("/forgot-password")
    public String getform(){
        return "security/forgot-password";
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email,String token, Model model) {
        // Tìm người dùng với email được cung cấp
        Account user = userService.findbyEmail(email);
        if (user == null) {
            return "security/forgot-password-error";
        }

        // Tạo token và gửi email đặt lại mật khẩu
        PasswordResetToken resetToken = userService.generateToken(user);
        userService.sendResetEmail(user, resetToken);
        model.addAttribute("token", token);
        return "security/reset-password";
    }
//    @GetMapping("/reset-password")
//    public String getResetPasswordForm() {
//
//        return "security/reset-password";
//    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        // Tìm token đặt lại mật khẩu
        PasswordResetToken resetToken = userService.findPasswordResetToken(token);
        if (resetToken == null) {
            return "security/reset-password-error";
        }

        // Kiểm tra xem token đã hết hạn chưa
        if (resetToken.isExpired()) {
            return "security/reset-password-error";
        }

        // Lấy người dùng từ token
        Account user = resetToken.getUsername();

        // Đặt lại mật khẩu mới cho người dùng
        userService.resetPassword(user, newPassword);

        // Xóa token đặt lại mật khẩu
        userService.deletePasswordResetToken(resetToken);

        return "security/reset-password-success";
    }
}
