package com.poly.da2.service;

import com.poly.da2.entity.Notification;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface NotificationService {
    void sendNotificationToAdmin(HttpSession session, String message);
    List<String> getNotifications(HttpSession session);
    void clearNotifications(HttpSession session);
    List<Notification> findAll();

    List<Notification> findAll6();
}
