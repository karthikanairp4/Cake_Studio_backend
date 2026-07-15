package com.example.thecakestudio.replository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.thecakestudio.dto.CartItemDTO;
import com.example.thecakestudio.entity.CakeOptions;
import com.example.thecakestudio.entity.Cakes;
import com.example.thecakestudio.entity.CartItem;
import com.example.thecakestudio.entity.User;

public interface CartItemRepo extends JpaRepository<CartItem, Integer>{

	CartItem save(CartItemDTO cartDTO);

	CartItem getCartById(int id);

	List<CartItem> getByUserId(int id);

	List<CartItem> findByUser(User user);

	Optional<CartItem> findByUserAndCakeAndWeightAndMessageAndSpongeAndFillingAndFrosting(User user, Cakes cake,
			double weight, String message, CakeOptions sponge, CakeOptions filling, CakeOptions frosting);

}
