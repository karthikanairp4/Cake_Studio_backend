package com.example.thecakestudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.thecakestudio.entity.Cakes;
import com.example.thecakestudio.enums.CakeCategory;
import com.example.thecakestudio.service.CakeService;

@RestController
public class CakeController {
	
	@Autowired
	CakeService cakeService;
	
	@GetMapping("/getCakesByCategory/{category}")
	public List<Cakes> getCakesByCategory(@PathVariable CakeCategory category) {
		return cakeService.getCakesByCategory(category);
		
	}
	
	@GetMapping("/findCakeById/{id}")
	public Cakes findCakeById(@PathVariable int id) {
		return cakeService.findCakeById(id);
		
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Cakes>> search(
	        @RequestParam String keyword) {
	    return ResponseEntity.ok(cakeService.searchCakes(keyword));
	}

}
