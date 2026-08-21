package com.example.thecakestudio.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import com.example.thecakestudio.dto.AdminAIReportDTO;

@Service
public class PDFService {

	public byte[] generateDailySalesReport(LocalDate date, AdminAIReportDTO report, String aiAnalysis) {
		try {
			PDDocument document = new PDDocument();
			PDPage page = new PDPage(PDRectangle.LETTER);
			document.addPage(page);
			PDType1Font titleFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
			PDType1Font headingFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
			PDType1Font normalFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
			PDPageContentStream content = new PDPageContentStream(document, page);
			
			float y = 730;

			// ==========================================
			// TITLE
			// ==========================================

			content.beginText();
			content.setFont(titleFont, 22);
			content.newLineAtOffset(50, y);
			content.showText("Paprika Bakes");
			content.endText();
			
			y -= 35;
			content.beginText();
			content.setFont(headingFont, 16);
			content.newLineAtOffset(50, y);
			content.showText("Daily Sales Report");
			content.endText();

			y -= 25;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
			content.beginText();
			content.setFont(normalFont, 11);
			content.newLineAtOffset(50, y);
			content.showText("Report Date: " + date.format(formatter));
			content.endText();

			y -= 40;

			// ==========================================
			// SALES SUMMARY
			// ==========================================
			y = writeHeading(content, headingFont, "Sales Summary", y);
			y = writeLine(content, normalFont, "Total Revenue: ₹" + formatMoney(report.getTotalRevenue()), y);
			y = writeLine(content, normalFont, "Total Orders: " + report.getTotalOrders(), y);
			y = writeLine(content, normalFont, "Average Order Value: ₹" + formatMoney(report.getAverageOrderValue()),
					y);

			y -= 10;

			// ==========================================
			// ORDER STATUS
			// ==========================================

			y = writeHeading(content, headingFont, "Order Status", y);

			y = writeLine(content, normalFont, "Pending Orders: " + report.getPendingOrders(), y);

			y = writeLine(content, normalFont, "Confirmed Orders: " + report.getConfirmedOrders(), y);

			y = writeLine(content, normalFont, "Completed Orders: " + report.getCompletedOrders(), y);

			y = writeLine(content, normalFont, "Cancelled Orders: " + report.getCancelledOrders(), y);

			y -= 10;

			// ==========================================
			// TOP SELLING CAKES
			// ==========================================

			y = writeHeading(content, headingFont, "Top Selling Cakes", y);
			if (report.getTopSellingCakes() != null && !report.getTopSellingCakes().isEmpty()) {
				for (String cake : report.getTopSellingCakes()) {
					y = writeLine(content, normalFont, "- " + cake, y);
				}
			} else {
				y = writeLine(content, normalFont, "No cake sales recorded.", y);
			}
			y -= 10;

			// ==========================================
			// AI ANALYSIS
			// ==========================================

			y = writeHeading(content, headingFont, "AI Sales Analysis", y);
			String[] paragraphs = aiAnalysis.split("\\r?\\n");
			for (String paragraph : paragraphs) {
				paragraph = paragraph.trim();
				if (paragraph.isEmpty()) {
					y -= 8;
					continue;
				}

				/*
				 * If the AI report becomes too long for the first page, create another page.
				 */
				if (y < 80) {
				    content.close();
				    page = new PDPage(PDRectangle.LETTER);
				    document.addPage(page);
					content = new PDPageContentStream(document, page
				            );
				    y = 730;
				}

				y = writeWrappedText(content, normalFont, paragraph, y);
				y -= 8;
			}

			// ==========================================
			// FOOTER
			// ==========================================

			content.beginText();
			content.setFont(normalFont, 8);
			content.newLineAtOffset(50, 40);
			content.showText("Generated automatically by Paprika Bakes");
			content.endText();
			content.close();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			document.save(output);
			document.close();
			return output.toByteArray();

		} catch (IOException e) {

			throw new RuntimeException("Failed to generate PDF report", e);
		}
	}

	private float writeHeading(PDPageContentStream content, PDFont font, String text, float y) throws IOException {
		content.beginText();
		content.setFont(font, 13);
		content.newLineAtOffset(50, y);
		content.showText(text);
		content.endText();

		return y - 22;
	}

	private float writeLine(PDPageContentStream content, PDFont font, String text, float y) throws IOException {
		content.beginText();
		content.setFont(font, 11);
		content.newLineAtOffset(50, y);
		content.showText(text);
		content.endText();

		return y - 18;
	}

	private float writeWrappedText(PDPageContentStream content, PDFont font, String text, float y) throws IOException {
		final int maxCharacters = 95;
		String[] words = text.split(" ");
		StringBuilder line = new StringBuilder();
		for (String word : words) {
			if (line.length() + word.length() + 1 > maxCharacters) {
				y = writeLine(content, font, line.toString(), y);
				line.setLength(0);
			}

			if (line.length() > 0) {
				line.append(" ");
			}

			line.append(word);
		}

		if (line.length() > 0) {
			y = writeLine(content, font, line.toString(), y);
		}

		return y;
	}

	private String formatMoney(Double value) {
		if (value == null) {
			return "0.00";
		}

		return String.format("%.2f", value);
	}
}