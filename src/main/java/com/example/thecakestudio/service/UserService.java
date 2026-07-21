package com.example.thecakestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.thecakestudio.dto.ProfileDTO;
import com.example.thecakestudio.dto.UserDTO;
import com.example.thecakestudio.entity.User;
import com.example.thecakestudio.enums.Role;
import com.example.thecakestudio.replository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PasswordEncoder encoder;
	

	public User registerUser(UserDTO userDTO) {
		User user = new User();
		String email = userDTO.getEmail();
		if(userRepo.existsByEmail(email)) {
			throw new RuntimeException("User Already Registered");
		} else {
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setEmail(userDTO.getEmail());
			user.setPassword(encoder.encode(userDTO.getPassword()));
			user.setPhone(userDTO.getPhone());
			user.setRole(Role.CUSTOMER);
			userRepo.save(user);
		}
		
		return user;
	}

	public User login(String email, String password) {
		User user = userRepo.findByEmail(email);
	    if (user == null) {
	        throw new RuntimeException("User not found");
	    }
		if(encoder.matches(password, user.getPassword())) {
			return user;
		} else {
			throw new RuntimeException ("Invalid Credientials");
		}
	}
	
	public User processGoogleUser(String email, String fullName) {
		User user = userRepo.findByEmail(email);
		
		if (user == null) {
			user = new User();
			user.setEmail(email);
			String[] names = fullName.trim().split("\\s+", 2);
			user.setFirstName(names[0]);
			if (names.length > 1) {
			    user.setLastName(names[1]);
			}
			user.setRole(Role.CUSTOMER);
			user.setPassword(encoder.encode(java.util.UUID.randomUUID().toString()));
			user = userRepo.save(user);
		}
		return user;
	}

	public ProfileDTO getProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(authentication.getName());
        ProfileDTO dto = new ProfileDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setStreet(user.getStreet());
        dto.setCity(user.getCity());
        dto.setProvince(user.getProvince());
        dto.setPostalCode(user.getPostalCode());
        return dto;
    }
	
	public ProfileDTO updateProfile(ProfileDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(authentication.getName());
        user.setPhone(dto.getPhone());
        user.setStreet(dto.getStreet());
        user.setCity(dto.getCity());
        user.setProvince(dto.getProvince());
        user.setPostalCode(dto.getPostalCode());
        userRepo.save(user);
        dto.setEmail(user.getEmail());
        return dto;
    }

}
