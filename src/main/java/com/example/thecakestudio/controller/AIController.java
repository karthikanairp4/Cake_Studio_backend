package com.example.thecakestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
