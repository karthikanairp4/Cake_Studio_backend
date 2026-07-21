package com.example.thecakestudio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.thecakestudio.dto.CartItemDTO;
import com.example.thecakestudio.dto.CartUpdateDTO;
import com.example.thecakestudio.entity.CakeOptions;
import com.example.thecakestudio.entity.Cakes;
import com.example.thecakestudio.entity.CartItem;
import com.example.thecakestudio.entity.User;
import com.example.thecakestudio.replository.CakeOptionsRepo;
import com.example.thecakestudio.replository.CakeRepo;
import com.example.thecakestudio.replository.CartItemRepo;
import com.example.thecakestudio.replository.UserRepo;

@Service
public class CartItemService {

	@Autowired
	CartItemRepo itemRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	CakeRepo cakesRepo;

	@Autowired
	CakeOptionsRepo optionsRepo;

	public CartItem addToCart(CartItemDTO cartDTO) {
		System.out.println("Sponge ID: " + cartDTO.getSpongeId());
		System.out.println("Filling ID: " + cartDTO.getFillingId());
		System.out.println("Frosting ID: " + cartDTO.getFrostingId());
		CartItem item = new CartItem();
		User user = userRepo.findById(cartDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
		Cakes cake = cakesRepo.findById(cartDTO.getCakeId()).orElseThrow(() -> new RuntimeException("Cake not found"));
		
		CakeOptions sponge = null;
		CakeOptions filling = null;
		CakeOptions frosting = null;

		if (cartDTO.getSpongeId() != null) {
			sponge = optionsRepo.findById(cartDTO.getSpongeId())
					.orElseThrow(() -> new RuntimeException("Sponge not found"));
			item.setSponge(sponge);
		}

		if (cartDTO.getFillingId() != null) {
			filling = optionsRepo.findById(cartDTO.getFillingId())
					.orElseThrow(() -> new RuntimeException("Filling not found"));
			item.setFilling(filling);
		}

		if (cartDTO.getFrostingId() != null) {
			frosting = optionsRepo.findById(cartDTO.getFrostingId())
					.orElseThrow(() -> new RuntimeException("Frosting not found"));
			item.setFrosting(frosting);

		}

		Optional<CartItem> existingItem = itemRepo.findByUserAndCakeAndWeightAndMessageAndSpongeAndFillingAndFrosting(
				user, cake, cartDTO.getWeight(), cartDTO.getMessage(), sponge, filling, frosting);
		if (existingItem.isPresent()) {
			CartItem existing = existingItem.get();
			existing.setQuantity(existing.getQuantity() + cartDTO.getQuantity());
			return itemRepo.save(existing);
		}
		item.setUser(user);
		item.setCake(cake);
		item.setQuantity(cartDTO.getQuantity());
		item.setWeight(cartDTO.getWeight());

		if (cartDTO.getMessage() != null) {
			item.setMessage(cartDTO.getMessage());
		}

		return itemRepo.save(item);
	}

	public List<CartItem> getCartByUserID(int id) {
		List<CartItem> cartitem = itemRepo.getByUserId(id);
		return cartitem;
	}

	public ResponseEntity<String> deleteItem(int id) {
		CartItem item = itemRepo.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
		itemRepo.delete(item);
		return ResponseEntity.ok("Item deleted successfully");
	}

	public CartItem updateItem(int cartId, CartUpdateDTO updateDTO) {
		CartItem item = itemRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Item not found"));

		if (updateDTO.getQuantity() != null) {
			item.setQuantity(updateDTO.getQuantity());
		}
		if (updateDTO.getWeight() != null) {
			item.setWeight(updateDTO.getWeight());
		}
		if (updateDTO.getMessage() != null) {
			item.setMessage(updateDTO.getMessage());

		}

		// Update sponge
		if (updateDTO.getSponge_id() != null) {
			CakeOptions sponge = optionsRepo.findById(updateDTO.getSponge_id())
					.orElseThrow(() -> new RuntimeException("Sponge not found"));

			item.setSponge(sponge);
		}

		// Update filling
		if (updateDTO.getFilling_id() != null) {
			CakeOptions filling = optionsRepo.findById(updateDTO.getFilling_id())
					.orElseThrow(() -> new RuntimeException("Filling not found"));

			item.setFilling(filling);
		}

		// Update frosting
		if (updateDTO.getFrosting_id() != null) {
			CakeOptions frosting = optionsRepo.findById(updateDTO.getFrosting_id())
					.orElseThrow(() -> new RuntimeException("Frosting not found"));

			item.setFrosting(frosting);
		}

		return itemRepo.save(item);
	}

}
