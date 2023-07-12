package com.poly.da2.service;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}
