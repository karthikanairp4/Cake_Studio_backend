package com.example.thecakestudio.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.thecakestudio.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiry}")
	private long expiry;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String generateToken(User user) {
		return Jwts.builder()
				.claim("userId", user.getId())
				.claim("role", user.getRole())
				.subject(user.getEmail())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiry))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith((javax.crypto.SecretKey) getSigningKey()).build().parseSignedClaims(token)
				.getPayload();
	}

	/**
	 * Extract email (subject)
	 */
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	/**
	 * Extract user id
	 */
	public Integer extractUserId(String token) {
		return extractAllClaims(token).get("userId", Integer.class);
	}

	/**
	 * Check whether token is expired
	 */
	public boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}

	/**
	 * Validate JWT
	 */
	public boolean validateToken(String token) {
		try {
			return !isTokenExpired(token);
		} catch (Exception e) {
			return false;
		}
	}

}
