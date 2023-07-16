package com.poly.da2.repository;


import com.poly.da2.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Query("SELECT o FROM Order o Where o.user.account.username=?1 and o.status_order=?2")
    List<Order> findByUsername(String username, String status);
    @Query("SELECT o FROM Order o Where o.status_order=?1")
    List<Order> getByOderStatus(String status);
    @Query("SELECT o FROM Order o Where o.user.account.username=?1 and o.status_order=?2")
    List<Order> findByUsernameAndStatus(String username, String status);
}
