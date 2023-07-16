package com.poly.da2.controller;

import com.poly.da2.entity.Account;
import com.poly.da2.entity.Authority;
import com.poly.da2.entity.Role;
import com.poly.da2.entity.Userss;
import com.poly.da2.repository.AccountRepository;
import com.poly.da2.repository.AuthorityRepository;
import com.poly.da2.repository.UserRepository;
import com.poly.da2.service.LoginService;
import com.poly.da2.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {
    @Autowired
    LoginService loginService;
    @Autowired
    ParamService paramService;
    @Autowired
    AccountRepository accdao;
    @Autowired
    UserRepository udao;

    @Autowired
    AuthorityRepository adao;
    @Autowired
    HttpServletRequest request;

    @RequestMapping("/security/login/form")
    public String loginForm(Model model) {
        model.addAttribute("message", "Vui lòng đăng nhập!");
        model.addAttribute("message2", "Vui lòng đăng ký!");
        return "security/login";
    }

    @RequestMapping("/security/login/success")
    public String loginSuccess(Model model) {
        model.addAttribute("message", "Đăng nhập thành công!");
        return "security/login";
    }

    @RequestMapping("/security/login/error")
    public String loginError(Model model) {
        model.addAttribute("message", "Sai thông tin đăng nhập!");
        return "security/login";
    }

    @RequestMapping("/security/logoff/success")
    public String logoff(Model model) {
        model.addAttribute("message", "Đăng xuất thành công!");
        return "security/login";
    }

    @RequestMapping("/security/unauthoried")
    public String unauthoried(Model model) {
        model.addAttribute("message", "Bạn không có quyền truy xuất!");
        return "security/login";
    }

    @RequestMapping("/oauth2/login/success")
    public String success(OAuth2AuthenticationToken oauth2,@AuthenticationPrincipal OAuth2User principal) {
        loginService.loginFormOAuth2(oauth2,principal);
        return "redirect:/security/login/success";
    }

    @PostMapping("/register")
    public String register(Account nd, Userss u) {
        // Đọc các tham số từ form sign up (username, email, password, repeat pass, check agree)
        String username = paramService.getString("username", "");
        String password = paramService.getString("password", "");
        String email = paramService.getString("email", "");
        String re_pass = paramService.getString("repass", "");
        if (nd.getPassword().equals(re_pass)) {
            u.setFullName("NoName");
            nd.setGmail(email);
            u.setGmail(email);
            u.setPhoto("noimage.png");
            nd.setPassword(password);
            nd.setUsername(username);
            accdao.save(nd);
            udao.save(u);
            request.setAttribute("messageS", "Đăng ký thành công bạn có thể đăng nhập ngay bây giờ!");

        } else {
            request.setAttribute("messageS", "Mật khẩu và nhập lại mật khẩu không trùng khớp!");
        }

        return "redirect:/security/login/success";
    }
}
