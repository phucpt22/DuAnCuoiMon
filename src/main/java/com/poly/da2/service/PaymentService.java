package com.poly.da2.service;

import com.paypal.api.payments.*;
import com.paypal.base.exception.PayPalException;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.poly.da2.entity.Order;
import com.poly.da2.entity.Payments;
import com.poly.da2.repository.OrderRepository;
import com.poly.da2.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class PaymentService {
    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    private static final String MODE = "sandbox";
    private final Map<String, String> sdkConfig = new HashMap<String, String>() {{
        put("mode", MODE);
    }};
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    OrderRepository orderRepository;

    public Payment createPayment(BigDecimal total, String currency, String method, String intent, String description,
                                 String cancelUrl, String successUrl) throws PayPalRESTException {
        Payment payment = null;

        // Tạo access token lấy từ PayPal
        String accessToken = new OAuthTokenCredential(clientId, clientSecret).getAccessToken();

        // Thông tin mặc định cho giao dịch
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(total.toString());

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(description);

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        // Tạo payment với các thông tin thanh toán
        payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        // Gọi API để tạo payment và trả về kết quả
        Payment createdPayment = payment.create(new APIContext(accessToken) {
            {
                setConfigurationMap(sdkConfig);
            }
        });
        return createdPayment;
    }


    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = null;

        String accessToken = new OAuthTokenCredential(clientId, clientSecret).getAccessToken();
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);

        // Thực hiện thanh toán với paymentId và payerId
        payment = new Payment().setId(paymentId).execute(new APIContext(accessToken) {
            {
                setConfigurationMap(sdkConfig);
            }
        }, paymentExecute);

        return payment;
    }
    public void savePayment(Payments payment, Order order) {
        payment.setAmount(payment.getAmount());
        payment.setPaymentId(payment.getPaymentId());
        payment.setPaymentId(payment.getPaymentId());
        payment.setOrder(order);
        paymentRepository.save(payment);
    }

    public void updateOrder(Order order) {
        order.setStatus_order("PAID");
        orderRepository.save(order);
    }

}
