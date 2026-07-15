package com.example.thecakestudio.replository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.thecakestudio.entity.Cakes;
import com.example.thecakestudio.enums.CakeCategory;

@Repository
public interface CakeRepo extends JpaRepository<Cakes, Integer> {

	List<Cakes> findAll();

	List<Cakes> findAllByCategory(CakeCategory category);

}
