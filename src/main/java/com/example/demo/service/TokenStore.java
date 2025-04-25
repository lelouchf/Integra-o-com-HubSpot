package com.example.demo.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

@Service
public class TokenStore {
	private String accessToken;
	private String refreshToken;
	private Instant expiresAt;

	public void saveToken(String accessToken, String refreshToken, int expiresInSeconds) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresAt = Instant.now().plusSeconds(expiresInSeconds);
	}

	public String getAccessToken() {
		if (Instant.now().isAfter(expiresAt)) {
			throw new RuntimeException("Token expirado! Faça o refresh ou nova autenticação.");
		}
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public boolean isTokenExpired() {
		return Instant.now().isAfter(expiresAt);
	}
}
