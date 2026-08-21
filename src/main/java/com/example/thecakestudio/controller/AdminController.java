package com.example.thecakestudio.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.thecakestudio.dto.AdminAIReportDTO;
import com.example.thecakestudio.dto.AdminDashboardDTO;
import com.example.thecakestudio.entity.CakeOptions;
import com.example.thecakestudio.entity.Cakes;
import com.example.thecakestudio.entity.Order;
import com.example.thecakestudio.enums.OrderStatus;
import com.example.thecakestudio.replository.CakeOptionsRepo;
import com.example.thecakestudio.service.AdminService;
import com.example.thecakestudio.service.CakeOptionsService;
import com.example.thecakestudio.service.CakeService;

@RestController
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	CakeService cakeService;
	
	@Autowired
	CakeOptionsService cakeOptionsService;
	

	@GetMapping("/admin/dashboard")
	public AdminDashboardDTO getDashboard() {
		return adminService.getDashboardStats();
	}

	@GetMapping("/admin/orders")
	public List<Order> getAllOrders() {
		return adminService.getAllOrders();
	}

	@PutMapping("/admin/{id}/status")
	public Order updateOrderStatus(@PathVariable Integer id, @RequestParam OrderStatus status) {

		return adminService.updateOrderStatus(id, status);
	}
	
	@GetMapping("/admin/dashboard/revenue")
	public List<Map<String, Object>> getRevenueOverview(
	        @RequestParam(defaultValue = "7") int days) {

	    return adminService.getRevenueOverview(days);
	}
	
	@GetMapping("/admin/cake-options")
	public List<CakeOptions> getAllCakeOptions() {
	    return cakeOptionsService.getAllOptionsForAdmin();
	}
	
	@PostMapping("/admin/cake-options")
	public CakeOptions addCakeOption(@RequestBody CakeOptions option) {
		return cakeOptionsService.addOption(option);
	}
	
	@PutMapping("/admin/cake-options/{id}")
	public CakeOptions updateCakeOption(@PathVariable Integer id, @RequestBody CakeOptions option) {
		return cakeOptionsService.updateOption(id, option);
	}

	@PutMapping("/admin/cake-options/{id}/status")
	public CakeOptions updateCakeOptionStatus(@PathVariable Integer id, @RequestParam boolean active) {
		return cakeOptionsService.updateOptionStatus(id, active);
	}
	
	@GetMapping("/admin/cakes")
	public List<Cakes> getAllCakesForAdmin() {
	    return cakeService.getAllCakesForAdmin();
	}
	
	@PutMapping("/admin/cakes/{id}/status")
	public Cakes updateCakeStatus(@PathVariable Integer id, @RequestParam boolean active) {
		return cakeService.updateCakeStatus(id, active);
	}
	
	@PostMapping("/admin/cakes")
	public Cakes addCake(@RequestBody Cakes cake) {
		return cakeService.addOption(cake);
	}
	
	@PutMapping("/admin/cakes/{id}")
	public Cakes updateCake(@PathVariable Integer id, @RequestBody Cakes cake) {
		return cakeService.updateCake(id, cake);
	}
	
	@GetMapping("/admin/ai/report")
	public AdminAIReportDTO getAIReport() {
	    return adminService.getAIReport();
	}
	
	@GetMapping("/admin/ai/daily-report")
	public AdminAIReportDTO getDailyReport(@RequestParam LocalDate date) {
		return adminService.generateDailyReport(date);
	}
}
