package com.example.thecakestudio.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.thecakestudio.dto.BuyNowRequestDTO;
import com.example.thecakestudio.dto.PaymentIntentResponseDTO;
import com.example.thecakestudio.entity.CakeOptions;
import com.example.thecakestudio.entity.Cakes;
import com.example.thecakestudio.entity.CartItem;
import com.example.thecakestudio.entity.User;
import com.example.thecakestudio.replository.CakeOptionsRepo;
import com.example.thecakestudio.replository.CakeRepo;
import com.example.thecakestudio.replository.CartItemRepo;
import com.example.thecakestudio.replository.UserRepo;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
public class PaymentService {
	
    @Autowired
    UserRepo userRepo;

    @Autowired
    CartItemRepo cartItemRepo;
    
    @Autowired
    OrderService orderService;
    
    @Autowired
    CakeRepo cakeRepo;
    
    @Autowired
    CakeOptionsRepo optionRepo;

	public PaymentIntentResponseDTO createPaymentIntent() throws StripeException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepo.findByEmail(email);
        List<CartItem> cartItems = cartItemRepo.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double total = 0;
     
        for (CartItem item : cartItems) {
            total += orderService.calculatePrice(item.getCake(),item.getSponge(), item.getFilling(), item.getFrosting(),item.getWeight(),item.getQuantity());
        }

//        long amountInCents = Math.round(total * 100);
//        Map<String, Object> params = new HashMap<>();
//        params.put("amount", amountInCents);
//        params.put("currency", "cad");
//        params.put("automatic_payment_methods",
//                Map.of("enabled", true));
//
//        PaymentIntent paymentIntent = PaymentIntent.create(params);
//        
//        return new PaymentIntentResponseDTO(paymentIntent.getClientSecret());
        return createStripePaymentIntent(total);
    }

	public PaymentIntentResponseDTO createBuyNowPaymentIntent(BuyNowRequestDTO request) throws StripeException {
		System.out.println(request.getCakeId()+"request");
	    Cakes cake = cakeRepo.findById(request.getCakeId()).orElseThrow();
	    
	    CakeOptions sponge = request.getSpongeId() == null ? null : optionRepo.findById(request.getSpongeId()).orElse(null);
	    CakeOptions filling = request.getFillingId() == null ? null : optionRepo.findById(request.getFillingId()).orElse(null);
	    CakeOptions frosting = request.getFrostingId() == null ? null : optionRepo.findById(request.getFrostingId()).orElse(null);

	    double total = orderService.calculatePrice(cake, sponge, filling, frosting, request.getWeight(), request.getQuantity());
	    
	    return createStripePaymentIntent(total);
	}
	
	private PaymentIntentResponseDTO createStripePaymentIntent(double total) throws StripeException {
	    long amountInCents = Math.round(total * 100);
	    Map<String,Object> params = new HashMap<>();
	    params.put("amount", amountInCents);
	    params.put("currency", "cad");
	    params.put(
	            "automatic_payment_methods",
	            Map.of("enabled", true)
	    );

	    PaymentIntent paymentIntent = PaymentIntent.create(params);
	    return new PaymentIntentResponseDTO(paymentIntent.getClientSecret());
	}
	
}
