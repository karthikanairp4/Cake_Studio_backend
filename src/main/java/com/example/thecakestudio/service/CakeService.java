package com.example.thecakestudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thecakestudio.entity.Cakes;
import com.example.thecakestudio.enums.CakeCategory;
import com.example.thecakestudio.replository.CakeRepo;

@Service
public class CakeService {
	
	@Autowired
	CakeRepo cakeRepo;

	public List<Cakes> getCakesByCategory(CakeCategory category) {
		List<Cakes> allCakes = cakeRepo.findAllByCategory(category);
		return allCakes;
	}

	public Cakes findCakeById(int id) {
		Cakes cakeById = cakeRepo.findById(id).orElse(null);
		return cakeById;
	}

}
