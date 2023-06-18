package com.poly.da2.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PayPalService {
    public Payment createPayment(
                                 Double originnal_price,
                                 String cancelUrl,
                                 String successUrl)throws PayPalRESTException;
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
