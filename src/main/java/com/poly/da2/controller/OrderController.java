package com.poly.da2.controller;

import com.poly.da2.entity.Order;
import com.poly.da2.entity.Userss;
import com.poly.da2.repository.AccountRepository;
import com.poly.da2.repository.UserRepository;
import com.poly.da2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    AccountRepository accRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/order/checkout")
    public String checkout(Model model, Principal principal) {
        String username = principal.getName(); // Lấy tên đăng nhập
        Userss userss = userRepository.findByUserName(username);
        Integer userId = userss.getId();
        model.addAttribute("userId", userId); // Truyền ID của user qua Model
        return "cart/checkout";
    }

    @RequestMapping("/order/list")
    public String list(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        List<Order> cxn,dg,dh,dgh;
        cxn = orderService.findByUsername(username, "Chờ xác nhận");
        dg = orderService.findByUsername(username, "Đang giao");
        dh = orderService.findByUsername(username, "Đã huỷ");
        dgh = orderService.findByUsername(username, "Đã giao");
        model.addAttribute("cxn", cxn);
        model.addAttribute("dg", dg);
        model.addAttribute("dh", dh);
        model.addAttribute("dgh", dgh);
        return "order/list";
    }


    @RequestMapping("/order/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        return "order/detail";
    }
}
