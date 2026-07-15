package com.example.thecakestudio.dto;

public class PaymentIntentResponseDTO {

    private String clientSecret;

    public PaymentIntentResponseDTO(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}
