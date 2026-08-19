package com.example.thecakestudio.dto;

import java.util.List;

public class AdminAIReportDTO {

	private Double totalRevenue;
	private long totalOrders;
	private long pendingOrders;
	private long confirmedOrders;
	private long completedOrders;
	private long cancelledOrders;
	private Double averageOrderValue;
	private List<String> topSellingCakes;

	public AdminAIReportDTO(Double totalRevenue, long totalOrders, long pendingOrders, long confirmedOrders,
			long completedOrders, long cancelledOrders, Double averageOrderValue, List<String> topSellingCakes) {
		super();
		this.totalRevenue = totalRevenue;
		this.totalOrders = totalOrders;
		this.pendingOrders = pendingOrders;
		this.confirmedOrders = confirmedOrders;
		this.completedOrders = completedOrders;
		this.cancelledOrders = cancelledOrders;
		this.averageOrderValue = averageOrderValue;
		this.topSellingCakes = topSellingCakes;
	}

	public Double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public long getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(long totalOrders) {
		this.totalOrders = totalOrders;
	}

	public long getPendingOrders() {
		return pendingOrders;
	}

	public void setPendingOrders(long pendingOrders) {
		this.pendingOrders = pendingOrders;
	}

	public long getConfirmedOrders() {
		return confirmedOrders;
	}

	public void setConfirmedOrders(long confirmedOrders) {
		this.confirmedOrders = confirmedOrders;
	}

	public long getCompletedOrders() {
		return completedOrders;
	}

	public void setCompletedOrders(long completedOrders) {
		this.completedOrders = completedOrders;
	}

	public long getCancelledOrders() {
		return cancelledOrders;
	}

	public void setCancelledOrders(long cancelledOrders) {
		this.cancelledOrders = cancelledOrders;
	}

	public Double getAverageOrderValue() {
		return averageOrderValue;
	}

	public void setAverageOrderValue(Double averageOrderValue) {
		this.averageOrderValue = averageOrderValue;
	}

	public List<String> getTopSellingCakes() {
		return topSellingCakes;
	}

	public void setTopSellingCakes(List<String> topSellingCakes) {
		this.topSellingCakes = topSellingCakes;
	}

}
