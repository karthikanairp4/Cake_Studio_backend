package com.example.thecakestudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thecakestudio.entity.CakeOptions;
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

	public List<Cakes> searchCakes(String keyword) {
	    return cakeRepo.searchCakes(keyword);
	}
	
	public List<Cakes> getAllCakesForAdmin() {
	    return cakeRepo.findAll();
	}

	public Cakes updateCakeStatus(Integer id, boolean active) {
		Cakes cake = cakeRepo.findById(id).orElseThrow(() -> new RuntimeException("Cake not found"));
		cake.setActive(active);

		return cakeRepo.save(cake);
	}

	public Cakes addOption(Cakes cake) {
		cake.setActive(true);
		return cakeRepo.save(cake);
	}

	public Cakes updateCake(Integer id, Cakes updatedCake) {
		Cakes cake = cakeRepo.findById(id).orElseThrow(() -> new RuntimeException("Cake not found"));
		cake.setName(updatedCake.getName());
		cake.setDescription(updatedCake.getDescription());
		cake.setBasePrice(updatedCake.getBasePrice());
		cake.setCategory(updatedCake.getCategory());
		cake.setImgUrl(updatedCake.getImgUrl());

		return cakeRepo.save(cake);
	}

}
