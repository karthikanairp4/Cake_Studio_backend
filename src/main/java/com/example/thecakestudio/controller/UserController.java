package com.example.thecakestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.thecakestudio.dto.LoginDTO;
import com.example.thecakestudio.dto.LoginResponseDTO;
import com.example.thecakestudio.dto.ProfileDTO;
import com.example.thecakestudio.dto.UserDTO;
import com.example.thecakestudio.entity.User;
import com.example.thecakestudio.security.JwtUtil;
import com.example.thecakestudio.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@GetMapping("/")
	public String getUser() {
		return "Hello";
	}
	
	@PostMapping("auth/register")
	public User registerUser(@RequestBody UserDTO userDTO) {
		return userService.registerUser(userDTO);
		
	}
	
	@PostMapping("auth/login")
	public LoginResponseDTO login(@RequestBody LoginDTO loginDTO) {
		User user = userService.login(loginDTO.getEmail(), loginDTO.getPassword());
		String token = jwtUtil.generateToken(user);
		LoginResponseDTO responseDTO = new LoginResponseDTO();
		responseDTO.setToken(token);
		responseDTO.setUserId(user.getId());
		responseDTO.setEmail(user.getEmail());
		
		return responseDTO;
	}
	
	@GetMapping("/profile")
    public ProfileDTO getProfile() {
        return userService.getProfile();
    }

    @PutMapping("/profile")
    public ProfileDTO updateProfile(@RequestBody ProfileDTO dto) {
        return userService.updateProfile(dto);
    }

}
