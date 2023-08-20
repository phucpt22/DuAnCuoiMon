package com.poly.da2.repository;

import com.poly.da2.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    @Query(value = "select top 6 * from notifications n order by n.create_date desc", nativeQuery = true)
    List<Notification> list();
}
