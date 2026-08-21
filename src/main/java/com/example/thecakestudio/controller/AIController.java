package com.example.thecakestudio.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.thecakestudio.dto.AdminAIReportDTO;
import com.example.thecakestudio.service.AIService;
import com.example.thecakestudio.service.AdminService;

@RestController
public class AIController {
	
	@Autowired
	AIService aiService;
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/admin/ai/insights")
    public String testAI() {
		AdminAIReportDTO report = adminService.getAIReport();
        return aiService.generateReportInsights(report);
    }
	
	@GetMapping("/admin/ai/daily-report")
	public ResponseEntity<byte[]> getDailyReport(@RequestParam LocalDate date) {
		byte[] pdf = adminService.generateDailyReportPdf(date);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales-report-" + date + ".pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}

}
