package com.example.thecakestudio.replository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}

