package com.example.thecakestudio.replository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.thecakestudio.entity.OrderItem;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {

	@Query("""
            SELECT oi.cake.name, SUM(oi.quantity)
            FROM OrderItem oi
            WHERE oi.order.status = com.example.thecakestudio.enums.OrderStatus.CONFIRMED
               OR oi.order.status = com.example.thecakestudio.enums.OrderStatus.COMPLETED
            GROUP BY oi.cake.name
            ORDER BY SUM(oi.quantity) DESC
            """)
    List<Object[]> findTopSellingCakes();

    @Query("""
            SELECT oi.cake.name, SUM(oi.quantity)
            FROM OrderItem oi
            JOIN oi.order o
            WHERE o.orderDate >= :start
            AND o.orderDate < :end
            AND o.status <> com.example.thecakestudio.enums.OrderStatus.CANCELLED
            GROUP BY oi.cake.name
            ORDER BY SUM(oi.quantity) DESC
            """)
    List<Object[]> findTopSellingCakesForDay(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
    
}

