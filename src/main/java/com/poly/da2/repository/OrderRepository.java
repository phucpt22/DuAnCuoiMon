package com.poly.da2.repository;


import com.poly.da2.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Query("SELECT o FROM Order o Where o.user.account.username=?1")
    List<Order> findByUsername(String username);

}