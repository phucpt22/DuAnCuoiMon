package com.poly.da2.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.poly.da2.service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PayPalServiceImpl implements PayPalService {
    @Autowired
    private APIContext apiContext;
    @Override
    public Payment createPayment(
                                 Double originnal_price,
                                 String cancelUrl,
                                 String successUrl) throws PayPalRESTException{
            Amount amount = new Amount();
            originnal_price = new BigDecimal(originnal_price).setScale(2, RoundingMode.HALF_UP).doubleValue();
            amount.setTotal(String.format("%.2f", originnal_price));

            Transaction transaction = new Transaction();

            transaction.setAmount(amount);

            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

            Payer payer = new Payer();
            Payment payment = new Payment();
            payment.getId();
            payment.setPayer(payer);
            payment.setTransactions(transactions);
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(cancelUrl);
            redirectUrls.setReturnUrl(successUrl);
            payment.setRedirectUrls(redirectUrls);

            return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
}
