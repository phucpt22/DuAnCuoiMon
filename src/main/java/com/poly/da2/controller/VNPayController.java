package com.poly.da2.controller;

import com.poly.da2.entity.Order;
import com.poly.da2.entity.Payments;
import com.poly.da2.repository.OrderRepository;
import com.poly.da2.repository.PaymentRepository;
import com.poly.da2.service.VnPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class VNPayController {
    @Autowired
    private VnPayService vnPayService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    OrderRepository orderRepository;
    @GetMapping("/submitOrder/{id}/{total_price}")
    public String submitOrder(@PathVariable(name = "id") int orderInfo,
                              @PathVariable(name = "total_price") String orderTotal,
                              HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(Integer.parseInt(orderTotal), String.valueOf(orderInfo), baseUrl);
        request.getSession().setAttribute("idOrder",orderInfo);
        return "redirect:" + vnpayUrl;
    }
//    @PostMapping("/submitOrder")
//    public String submidOrder(@RequestParam("amount") int orderTotal,
//                              @RequestParam("orderInfo") String orderInfo,
//                              HttpServletRequest request){
//        String vnpayUrl ="";
//        try {
//            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//            vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
//            return "redirect:" + vnpayUrl;
//        }catch (Exception e){
//            e.getMessage();
//            System.out.println(e);
//        }
//       return "redirect:" + vnpayUrl;
//    }

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
        request.getSession().removeAttribute("idOrder");
        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }
}
