package com.poly.da2.rest;

import com.poly.da2.service.OrderService;
import com.poly.da2.service.VnPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/vnpay")
public class VnPayRestController {
    @Autowired
    VnPayService vnPayService;
//    @GetMapping("/submitOrder")
//    public String submitOrder(@RequestParam("amount") double orderTotal,
//                              @RequestParam("orderInfo") String orderInfo,
//                              HttpServletRequest request){
//        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
//        return "redirect:" + vnpayUrl;
//    }
//
//    @GetMapping("/vnpay-payment")
//    public String GetMapping(HttpServletRequest request, Model model){
//        int paymentStatus =vnPayService.orderReturn(request);
//
//        String orderInfo = request.getParameter("vnp_OrderInfo");
//        String paymentTime = request.getParameter("vnp_PayDate");
//        String transactionId = request.getParameter("vnp_TransactionNo");
//        String totalPrice = request.getParameter("vnp_Amount");
//
//        model.addAttribute("orderId", orderInfo);
//        model.addAttribute("totalPrice", totalPrice);
//        model.addAttribute("paymentTime", paymentTime);
//        model.addAttribute("transactionId", transactionId);
//
//        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
//    }
}
