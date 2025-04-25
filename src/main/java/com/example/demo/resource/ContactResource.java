package com.example.demo.resource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.service.TokenStore;

@RestController
@RequestMapping(value = "/contacts")
public class ContactResource {

	@Autowired
	private TokenStore tokenStore;

	private final WebClient webClient = WebClient.create("https://api.hubapi.com");

	@PostMapping
	public ResponseEntity<String> createContact(@RequestBody Map<String, String> contactInfo) {

		String token = tokenStore.getAccessToken();

		 Map<String, Object> body = Map.of("properties", contactInfo);

	        return webClient.post()
	                .uri("https://api.hubapi.com/crm/v3/objects/contacts")
	                .header("Authorization", "Bearer " + token)
	                .contentType(MediaType.APPLICATION_JSON)
	                .bodyValue(body)
	                .retrieve()
	                .bodyToMono(String.class)
	                .map(ResponseEntity::ok)
	                .block();
	    }
	}

