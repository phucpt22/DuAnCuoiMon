

SELECT  MONTH(create_date) AS month, SUM(total_price) AS total_price
FROM orders
WHERE YEAR(create_date) = 2023
GROUP BY  MONTH(create_date)
ORDER BY MONTH(create_date) asc


SELECT p.name, p.image_urls,
       SUM(od.quantity),
       od.quantity * od.price AS total_money
FROM orders o
         INNER JOIN orderdetails od ON o.id = od.orderid
         INNER JOIN products p ON od.productid = p.id
GROUP BY p.name,  od.quantity, od.price, p.image_urls


SELECT MONTH(create_date) AS month, COUNT(*) AS new_users
FROM accounts
where YEAR(create_date) = 2023
GROUP BY  MONTH(create_date)
ORDER BY  month;