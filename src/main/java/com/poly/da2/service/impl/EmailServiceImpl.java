package com.poly.da2.service.impl;

import com.poly.da2.service.EmailService;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendEmail(String to, String subject, String content) {
        String username = "zzptpham231@gmail.com";
        String password = "inwwlmexzqkhanlj";
        String recipient = "phucptps19445@fpt.edu.vn";

        // Tạo session cho xác thực
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(getProperties(), authenticator);

        try {
            // Tạo đối tượng MimeMessage
            MimeMessage message = new MimeMessage(session);

            // Cấu hình các thông tin của email (người gửi, người nhận, tiêu đề, nội dung,...)
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Test Email");
            message.setText("This is a test email.");

            // Gửi email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Properties getProperties() {
        Properties properties = new Properties();

        // Cấu hình các thông tin của server SMTP
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "zzptpham231@gmail.com");
        properties.put("mail.smtp.port", "587");

        return properties;
    }
}
