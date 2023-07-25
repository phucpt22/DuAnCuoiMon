package com.poly.da2.repository;


import com.poly.da2.entity.Category;
import com.poly.da2.entity.RevenueByCateGory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("SELECT new RevenueByCateGory (c.id ,c.name, SUM(od.quantity * od.price) )\n" +
            "FROM Category c\n" +
            "INNER JOIN Product p ON c.id = p.category.id\n" +
            "INNER JOIN OrderDetail od ON p.id = od.product.id\n" +
            "INNER JOIN Order o on o.id = od.order.id\n" +
            "WHERE o.createDate BETWEEN  '2023-01-01' AND '2023-12-31' \n" +
            "GROUP BY c.name,c.id\n")
    List<RevenueByCateGory> getRevenueByCategory();

}
