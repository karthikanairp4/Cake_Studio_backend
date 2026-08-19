package com.example.thecakestudio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.thecakestudio.dto.AdminAIReportDTO;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import jakarta.annotation.PostConstruct;

@Service
public class AIService {

	private Client client;
	
	@Value("${gemini_api_key}")
	private String apiKey;
	
	@PostConstruct
	public void init() {
		this.client = Client.builder()
                .apiKey(apiKey)
                .build();
	}


    public String generateReportInsights(AdminAIReportDTO report) {
        String prompt = """
                You are an AI business analyst for a small bakery called The Cake Studio.

                Analyze the following sales data and provide useful business insights.

                Sales Data:
                Total Revenue: $%.2f
                Total Orders: %d
                Pending Orders: %d
                Confirmed Orders: %d
                Completed Orders: %d
                Cancelled Orders: %d
                Average Order Value: $%.2f

                Top Selling Cakes:
                %s

                Provide:
                1. A short overall performance summary.
                2. The strongest selling product.
                3. Any concern or issue visible in the data.
                4. Two practical recommendations for the bakery.

                Keep the response concise and easy for a bakery administrator to understand.
                Do not invent information that is not present in the data.
                """.formatted(
                    report.getTotalRevenue(),
                    report.getTotalOrders(),
                    report.getPendingOrders(),
                    report.getConfirmedOrders(),
                    report.getCompletedOrders(),
                    report.getCancelledOrders(),
                    report.getAverageOrderValue(),
                    String.join(", ", report.getTopSellingCakes())
                );

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.6-flash",
                        prompt,
                        null
                );

        return response.text();
    }

}
