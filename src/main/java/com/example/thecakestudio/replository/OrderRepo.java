package com.example.thecakestudio.replository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.thecakestudio.entity.Order;
import com.example.thecakestudio.entity.User;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

	List<Order> findByUserOrderByOrderDateDesc(User user);
}


