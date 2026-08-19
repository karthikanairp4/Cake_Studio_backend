package com.example.thecakestudio.dto;

public class AdminDashboardDTO {
	
	private Double todayRevenue;

    private long todayOrders;

    private long totalCustomers;

    private long pendingOrders;

	public AdminDashboardDTO(Double todayRevenue, long todayOrders, long totalCustomers, long pendingOrders) {
		this.todayRevenue=todayRevenue;
		this.todayOrders=todayOrders;
		this.totalCustomers=totalCustomers;
		this.pendingOrders=pendingOrders;
	}

	public Double getTodayRevenue() {
		return todayRevenue;
	}

	public void setTodayRevenue(Double todayRevenue) {
		this.todayRevenue = todayRevenue;
	}

	public long getTodayOrders() {
		return todayOrders;
	}

	public void setTodayOrders(long todayOrders) {
		this.todayOrders = todayOrders;
	}

	public long getTotalCustomers() {
		return totalCustomers;
	}

	public void setTotalCustomers(long totalCustomers) {
		this.totalCustomers = totalCustomers;
	}

	public long getPendingOrders() {
		return pendingOrders;
	}

	public void setPendingOrders(long pendingOrders) {
		this.pendingOrders = pendingOrders;
	}

}
