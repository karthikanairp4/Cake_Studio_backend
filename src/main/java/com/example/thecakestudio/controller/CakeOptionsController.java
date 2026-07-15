package com.example.thecakestudio.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.thecakestudio.entity.CakeOptions;
import com.example.thecakestudio.enums.OptionType;
import com.example.thecakestudio.service.CakeOptionsService;

@RestController
public class CakeOptionsController {
	
	@Autowired
	CakeOptionsService cakeOptionsService;
	
	@GetMapping("/all")
	public Map<String, Object> getAllOptions() {
		return cakeOptionsService.getAllOptions();
		
	}
	
}
