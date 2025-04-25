package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OAuthService {

	@Autowired
	private TokenStore tokenStore;

	@Value("${hubspot.client-id}")
	private String clientId;

	@Value("${hubspot.client-secret}")
	private String clientSecret;

	@Value("${hubspot.redirect-uri}")
	private String redirectUri;

	private final WebClient webClient = WebClient.create();

	public ResponseEntity<String> exchangeCodeForToken(String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "authorization_code");
		formData.add("client_id", clientId);
		formData.add("client_secret", clientSecret);
		formData.add("redirect_uri", redirectUri);
		formData.add("code", code);

		String tokenResponse = webClient.post().uri("https://api.hubapi.com/oauth/v1/token")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).bodyValue(formData).retrieve()
				.bodyToMono(String.class).block();

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(tokenResponse);
			String accessToken = node.get("access_token").asText();
			String refreshToken = node.get("refresh_token").asText();
			int expiresIn = node.get("expires_in").asInt();

			tokenStore.saveToken(accessToken, refreshToken, expiresIn);

			return ResponseEntity.ok("Token salvo com sucesso! Pronto para criar contatos.");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro ao processar token: " + e.getMessage());
		}

	}

}
