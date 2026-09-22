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
		this.client = Client.builder().apiKey(apiKey).build();
	}

	public String generateReportInsights(AdminAIReportDTO report) {
		String prompt = """
				You are an AI business analyst for a small bakery called The Cake Studio.

				Analyze the following daily sales data and create a concise daily sales analysis
				for the bakery administrator.

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

				Create the report using these sections:

				1. Overall Performance Summary
				2. Strongest Selling Product
				3. Concerns or Issues
				4. Practical Recommendations

				Rules:
				- Use ONLY the information provided.
				- Do not invent numbers, customers, products, or events.
				- Do not make assumptions that are not supported by the data.
				- If there is not enough data to identify a trend, clearly say so.
				- Keep the report concise.
				- Write for a bakery administrator.
				- Return plain text.
				- Do not use Markdown formatting.

				""".formatted(report.getTotalRevenue(), report.getTotalOrders(), report.getPendingOrders(),
				report.getConfirmedOrders(), report.getCompletedOrders(), report.getCancelledOrders(),
				report.getAverageOrderValue(), formatTopSellingCakes(report));

		GenerateContentResponse response = client.models.generateContent("gemini-3.5-flash-lite", prompt, null);

		return response.text();
	}

	private String formatTopSellingCakes(AdminAIReportDTO report) {
		if (report.getTopSellingCakes() == null || report.getTopSellingCakes().isEmpty()) {
			return "No cake sales recorded.";
		}

		return String.join(",", report.getTopSellingCakes());
	}

}
