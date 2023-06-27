package com.poly.da2.controller;

import com.poly.da2.entity.Order;
import com.poly.da2.entity.Payments;
import com.poly.da2.entity.Userss;
import com.poly.da2.repository.AccountRepository;
import com.poly.da2.repository.OrderRepository;
import com.poly.da2.repository.PaymentRepository;
import com.poly.da2.repository.UserRepository;
import com.poly.da2.service.OrderService;
import com.poly.da2.service.VnPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private VnPayService vnPayService;
    @Autowired
    private PaymentRepository paymentRepository;

    @RequestMapping("/order/checkout")
    public String checkout(Model model, Principal principal) {
        String username = principal.getName(); // Lấy tên đăng nhập
        Userss userss = userRepository.findByUserName(username);
        Integer userId = userss.getId();
        model.addAttribute("userId", userId); // Truyền ID của user qua Model
        return "cart/checkout";
    }

    @Transactional(readOnly = true)
    @RequestMapping("/order/list")
    public String list(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        List<Order> cxn, dg, dh, dgh;
        cxn = orderService.findByUsername(username, "Chờ xác nhận");
        dg = orderService.findByUsername(username, "Đang giao");
        dh = orderService.findByUsername(username, "Đã hủy");
        dgh = orderService.findByUsername(username, "Đã giao");
        model.addAttribute("cxn", cxn);
        model.addAttribute("dg", dg);
        model.addAttribute("dh", dh);
        model.addAttribute("dgh", dgh);
        return "order/list";
    }

    @RequestMapping("/order/cancel/{id}")
    public String cancel(@PathVariable("id") Integer id, Model model) {
        Order order = orderService.findById(id);
        order.setStatus_order("Đã hủy");
        orderRepository.save(order);
        return "redirect:/order/list";
    }

    @RequestMapping("/order/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        return "order/detail";
    }

    @GetMapping("/payment")
    public String showPaymentPage() {
        return "index";
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        int id =  (int) request.getSession().getAttribute("idOrder");
        Order oldOrder = orderRepository.findById(id).orElse(null);
        if (oldOrder != null) {
            // update the order properties
            oldOrder.setStatus_pay("Đã thanh toán");
            orderRepository.save(oldOrder);
        }
        Payments payments = new Payments();
        payments.setOrder_id(oldOrder);
        payments.setPayment_date(paymentTime);
        payments.setAmount(Double.valueOf(totalPrice));
        payments.setPayment_id(transactionId);
        paymentRepository.save(payments);
        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "cart/ordersuccess" : "cart/orderfail";
    }

}
