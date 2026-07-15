package com.example.thecakestudio.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thecakestudio.entity.CakeOptions;
import com.example.thecakestudio.enums.OptionType;
import com.example.thecakestudio.replository.CakeOptionsRepo;

@Service
public class CakeOptionsService {
	
	@Autowired
	CakeOptionsRepo optionsRepo;

	public List<CakeOptions> getAllOptionsbyType(OptionType type) {
		List<CakeOptions> data = optionsRepo.findAllByType(type);
		return data;
	}

	public Map<String, Object> getAllOptions() {
		Map<String, Object> response = new HashMap<>();
		response.put("sponges", optionsRepo.findByTypeOrderByPriceAsc(OptionType.SPONGE));
		response.put("fillings", optionsRepo.findByTypeOrderByPriceAsc(OptionType.FILLING));
		response.put("frostings", optionsRepo.findByTypeOrderByPriceAsc(OptionType.FROSTING));
		return response;
	}

}
