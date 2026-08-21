package com.example.thecakestudio.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thecakestudio.dto.AdminAIReportDTO;
import com.example.thecakestudio.dto.AdminDashboardDTO;
import com.example.thecakestudio.entity.Order;
import com.example.thecakestudio.enums.OrderStatus;
import com.example.thecakestudio.enums.PaymentStatus;
import com.example.thecakestudio.enums.Role;
import com.example.thecakestudio.replository.OrderItemRepo;
import com.example.thecakestudio.replository.OrderRepo;
import com.example.thecakestudio.replository.UserRepo;

@Service
public class AdminService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private OrderItemRepo orderItemRepo;

	public AdminDashboardDTO getDashboardStats() {
		LocalDate today = LocalDate.now();
		LocalDateTime startOfDay = today.atStartOfDay();
		LocalDateTime startOfTomorrow = today.plusDays(1).atStartOfDay();
		long todayOrders = orderRepo.countByOrderDateBetween(startOfDay, startOfTomorrow);
		Double todayRevenue = orderRepo.getRevenueBetween(startOfDay, startOfTomorrow, PaymentStatus.SUCCESS, OrderStatus.CONFIRMED);
		long totalCustomers = userRepo.countByRole(Role.CUSTOMER);
		long pendingOrders = orderRepo.countByStatus(OrderStatus.PENDING);

		return new AdminDashboardDTO(todayRevenue, todayOrders, totalCustomers, pendingOrders);
	}

	public List<Order> getAllOrders() {
		return orderRepo.findAllByOrderByOrderDateDesc();
	}

	public Order updateOrderStatus(Integer orderId, OrderStatus newStatus) {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
		order.setStatus(newStatus);
		return orderRepo.save(order);
	}

	public List<Map<String, Object>> getRevenueOverview(int days) {
	    List<Map<String, Object>> revenue = new ArrayList<>();
	    LocalDate today = LocalDate.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d");
	    for (int i = days - 1; i >= 0; i--) {
	        LocalDate date = today.minusDays(i);
	        LocalDateTime startOfDay = date.atStartOfDay();
	        LocalDateTime startOfNextDay = date.plusDays(1).atStartOfDay();
	        Double dailyRevenue = orderRepo.getRevenueBetween(
	                startOfDay,
	                startOfNextDay,
	                PaymentStatus.SUCCESS,
	                OrderStatus.CONFIRMED
	        );

	        if (dailyRevenue == null) {
	            dailyRevenue = 0.0;
	        }

	        Map<String, Object> data = new HashMap<>();
	        data.put("label", date.format(formatter));
	        data.put("revenue", dailyRevenue);

	        revenue.add(data);
	    }

	    return revenue;
	}

	public AdminAIReportDTO getAIReport() {

	    List<Order> orders = orderRepo.findAll();
	    long totalOrders = orders.size();
	    long pendingOrders = orderRepo.countByStatus(OrderStatus.PENDING);
	    long confirmedOrders = orderRepo.countByStatus(OrderStatus.CONFIRMED);
	    long completedOrders = orderRepo.countByStatus(OrderStatus.COMPLETED);
	    long cancelledOrders = orderRepo.countByStatus(OrderStatus.CANCELLED);
	    double totalRevenue = orders.stream()
	            .filter(order ->
	                    order.getPaymentStatus() == PaymentStatus.SUCCESS)
	            .filter(order ->
	                    order.getStatus() == OrderStatus.CONFIRMED
	                    || order.getStatus() == OrderStatus.COMPLETED)
	            .mapToDouble(Order::getTotalAmount)
	            .sum();

	    long validOrders = orders.stream()
	            .filter(order ->
	                    order.getPaymentStatus() == PaymentStatus.SUCCESS)
	            .filter(order ->
	                    order.getStatus() == OrderStatus.CONFIRMED
	                    || order.getStatus() == OrderStatus.COMPLETED)
	            .count();

	    double averageOrderValue = validOrders > 0
	            ? totalRevenue / validOrders
	            : 0;

	    List<Object[]> topCakesData =
	            orderItemRepo.findTopSellingCakes();

	    List<String> topSellingCakes = topCakesData.stream()
	            .limit(5)
	            .map(row -> row[0] + " - " + row[1] + " orders")
	            .toList();

	    return new AdminAIReportDTO(
	            totalRevenue,
	            totalOrders,
	            pendingOrders,
	            confirmedOrders,
	            completedOrders,
	            cancelledOrders,
	            averageOrderValue,
	            topSellingCakes
	    );
	}
	
	public AdminAIReportDTO generateDailyReport(LocalDate date) {
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.plusDays(1).atStartOfDay();
		long totalOrders = orderRepo.countDailyOrders(start, end);
		Double totalRevenue = orderRepo.getDailyRevenue(start, end, OrderStatus.CANCELLED);
		if (totalRevenue == null) {
			totalRevenue = 0.0;
		}

		long pendingOrders = orderRepo.countByOrderDateGreaterThanEqualAndOrderDateLessThanAndStatus(start, end,
				OrderStatus.PENDING);
		long confirmedOrders = orderRepo.countByOrderDateGreaterThanEqualAndOrderDateLessThanAndStatus(start, end,
				OrderStatus.CONFIRMED);
		long completedOrders = orderRepo.countByOrderDateGreaterThanEqualAndOrderDateLessThanAndStatus(start, end,
				OrderStatus.COMPLETED);
		long cancelledOrders = orderRepo.countByOrderDateGreaterThanEqualAndOrderDateLessThanAndStatus(start, end,
				OrderStatus.CANCELLED);
		Double averageOrderValue = 0.0;
		if (totalOrders > 0) {
			averageOrderValue = totalRevenue / totalOrders;
		}

		List<Object[]> results = orderItemRepo.findTopSellingCakesForDay(start, end);
		List<String> topSellingCakes = new ArrayList<>();
		for (Object[] row : results) {
			String cakeName = (String) row[0];
			Number quantity = (Number) row[1];
			topSellingCakes.add(cakeName + " - " + quantity.intValue() + " orders");
		}

		return new AdminAIReportDTO(totalRevenue, totalOrders, pendingOrders, confirmedOrders, completedOrders,
				cancelledOrders, averageOrderValue, topSellingCakes);
	}
	
	
}
