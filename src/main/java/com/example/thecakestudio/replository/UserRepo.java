package com.example.thecakestudio.replository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.thecakestudio.dto.UserDTO;
import com.example.thecakestudio.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

	boolean existsByEmail(String email);

	User findByEmail(String email);
}
