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
		response.put("sponges", optionsRepo.findByTypeAndActiveTrue(OptionType.SPONGE));
		response.put("fillings", optionsRepo.findByTypeAndActiveTrue(OptionType.FILLING));
		response.put("frostings", optionsRepo.findByTypeAndActiveTrue(OptionType.FROSTING));
		return response;
	}

	public List<CakeOptions> getAllOptionsForAdmin() {
		return optionsRepo.findAll();
	}

	public CakeOptions addOption(CakeOptions option) {
		return optionsRepo.save(option);
	}

	public CakeOptions updateOption(Integer id, CakeOptions updatedOption) {
		CakeOptions option = optionsRepo.findById(id).orElseThrow(() -> new RuntimeException("Cake option not found"));
		option.setName(updatedOption.getName());
		option.setType(updatedOption.getType());
		option.setPrice(updatedOption.getPrice());
		option.setImg(updatedOption.getImg());

		return optionsRepo.save(option);
	}

	public CakeOptions updateOptionStatus(Integer id, boolean active) {
		CakeOptions option = optionsRepo.findById(id).orElseThrow(() -> new RuntimeException("Cake option not found"));
		option.setActive(active);

		return optionsRepo.save(option);
	}


}
