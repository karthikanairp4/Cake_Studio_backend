package com.example.thecakestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.thecakestudio.dto.BuyNowRequestDTO;
import com.example.thecakestudio.dto.PaymentIntentResponseDTO;
import com.example.thecakestudio.service.PaymentService;
import com.stripe.exception.StripeException;

@RestController
public class PaymentController {
	
	 @Autowired
	 PaymentService paymentService;
	
	@PostMapping("/create-payment-intent")
    public PaymentIntentResponseDTO createPaymentIntent() throws StripeException {
        return paymentService.createPaymentIntent();

    }
	
	 @PostMapping("/create-buy-now-payment-intent")
	    public PaymentIntentResponseDTO createBuyNowPaymentIntent(
	            @RequestBody BuyNowRequestDTO request)
	            throws StripeException {
		 System.out.println("BUY NOW CONTROLLER");
	        return paymentService.createBuyNowPaymentIntent(request);
	    }
}
