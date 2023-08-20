package com.poly.da2.service.impl;

import com.poly.da2.entity.Notification;
import com.poly.da2.repository.NotificationRepository;
import com.poly.da2.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    @Override
    public void sendNotificationToAdmin(HttpSession session, String message) {
        List<String> notifications = (List<String>) session.getAttribute("notifications");

        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        notifications.add(message);
        session.setAttribute("notifications", notifications);
    }

    @Override
    public List<String> getNotifications(HttpSession session) {
        return (List<String>) session.getAttribute("notifications");
    }

    @Override
    public void clearNotifications(HttpSession session) {
        session.removeAttribute("notifications");
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> findAll6() {
        return notificationRepository.list();
    }
}
