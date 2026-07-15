package com.example.thecakestudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.thecakestudio.dto.CartItemDTO;
import com.example.thecakestudio.dto.CartUpdateDTO;
import com.example.thecakestudio.entity.CartItem;
import com.example.thecakestudio.service.CartItemService;

@RestController
public class CartItemController {
	
	@Autowired
	CartItemService cartItemService;

	@PostMapping("/addToCart")
	public CartItem addToCart(@RequestBody CartItemDTO cartDTO) {
		return cartItemService.addToCart(cartDTO);
	}
	
	@GetMapping("/cartItemByuser/{userID}")
	public List<CartItem> getCartByUserID(@PathVariable int userID) {
		return cartItemService.getCartByUserID(userID);
	}
	
	@DeleteMapping("/deleteItem/{cartID}")
	public ResponseEntity<String> deleteItem(@PathVariable int cartID){
		return cartItemService.deleteItem(cartID);
	}
	
	@PutMapping("/updateCart/{cartId}")
	public CartItem updateItem(@PathVariable int cartId, @RequestBody CartUpdateDTO updateDTO) {
		return cartItemService.updateItem(cartId, updateDTO);
		
	}
	
}

