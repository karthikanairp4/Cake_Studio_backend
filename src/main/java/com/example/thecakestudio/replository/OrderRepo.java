package com.example.thecakestudio.replository;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.thecakestudio.entity.Order;
import com.example.thecakestudio.entity.User;
import com.example.thecakestudio.enums.OrderStatus;
import com.example.thecakestudio.enums.PaymentStatus;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

	List<Order> findByUserOrderByOrderDateDesc(User user);

	long countByOrderDateBetween(LocalDateTime startOfDay, LocalDateTime startOfTomorrow);
	
	@Query("""
	        SELECT COALESCE(SUM(o.totalAmount), 0)
	        FROM Order o
	        WHERE o.orderDate >= :start
	        AND o.orderDate < :end
	        AND o.paymentStatus = :paymentStatus
	        AND o.status = :orderStatus
	    """)
	    Double getRevenueBetween(
	            @Param("start") LocalDateTime start,
	            @Param("end") LocalDateTime end,
	            @Param("paymentStatus") PaymentStatus paymentStatus,
	            @Param("orderStatus") OrderStatus orderStatus
	    );

	long countByStatus(OrderStatus status);

	List<Order> findAllByOrderByOrderDateDesc();
}


