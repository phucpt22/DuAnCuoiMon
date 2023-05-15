package com.poly.da2.repository;


import com.poly.da2.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query("SELECT p FROM OrderDetail p WHERE p.order.id = ?1")
    List<OrderDetail> findOrderDetailByOrdOrderById(Integer cid);
}
