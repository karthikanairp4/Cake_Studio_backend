package com.example.thecakestudio.replository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.thecakestudio.entity.CakeOptions;
import com.example.thecakestudio.enums.OptionType;

@Repository
public interface CakeOptionsRepo extends JpaRepository<CakeOptions, Integer> {

	List<CakeOptions> findAllByType(OptionType type);

	List<CakeOptions> findByTypeOrderByPriceAsc(OptionType sponge);

}
