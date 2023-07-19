package com.poly.da2.repository;


import com.poly.da2.entity.Order;
import com.poly.da2.entity.TopProduct;
import com.poly.da2.entity.TotalMoneyEachMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Query("SELECT o FROM Order o Where o.user.account.username=?1 and o.status_order=?2")
    List<Order> findByUsername(String username, String status);
    @Query("SELECT o FROM Order o Where o.status_order=?1")
    List<Order> getByOderStatus(String status);
    @Query("SELECT o FROM Order o Where o.user.account.username=?1 and o.status_order=?2")
    List<Order> findByUsernameAndStatus(String username, String status);
	@Query("SELECT o FROM Order o Where o.user.account.username=?1 and o.status_order=?2")
    List<Order> findByUsername(String username, String status);
    @Query("SELECT SUM(total_price) from Order where  convert(varchar(10), updateDate, 102) = convert(varchar(10), getdate(), 102)")
    long getTotalMoneyOrderToday();

    @Query("SELECT  new TotalMoneyEachMonth (MONTH(o.createDate), SUM(o.total_price))   FROM Order o WHERE YEAR(o.createDate) = ?1 GROUP BY  MONTH(o.createDate) ORDER BY MONTH(o.createDate) asc")
    List<TotalMoneyEachMonth>totalPriceSpecificYear(int year);


}
