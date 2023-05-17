package com.poly.da2.controller;

import com.poly.da2.entity.Account;
import com.poly.da2.entity.Userss;
import com.poly.da2.repository.AccountRepository;
import com.poly.da2.repository.UserRepository;
import com.poly.da2.service.LoginService;
import com.poly.da2.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
    HttpServletRequest request;

    @RequestMapping("/security/login/form")
    public String loginForm(Model model) {
        model.addAttribute("message", "Vui lòng đăng nhập!");
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
        return "product/store";
    }

    @RequestMapping("/security/unauthoried")
    public String unauthoried(Model model) {
        model.addAttribute("message", "Bạn không có quyền truy xuất!");
        return "security/login";
    }

    @RequestMapping("/oauth2/login/success")
    public String success(OAuth2AuthenticationToken oauth2) {
        loginService.loginFormOAuth2(oauth2);

        return "redirect:/security/login/success";
    }

    //tôi có class User có Id khóa chính tự tăng, và id đó là khóa ngoại của account, user mà account có mối quan hệ 1:1, đã dùng JPA reposity, tôi đã lưu thông tin tài khoản nhưng vẫn chưa lưu vào sql được vì Id tự tăng, tôi muốn khi đăng nhập bằng tài khoản google với Oauth2 lưu cả 2 đối tượng vào sql cùng lúc với spring boot
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

        return "redirect:/security/login/form";
    }
}
