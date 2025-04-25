package com.example.demo.resource;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/oauth")
public class OAuthResource {

	@Value("${hubspot.client-id}")
	private String clientId;

	@Value("${hubspot.redirect-uri}")
	private String redirectUri;
//
	@GetMapping("/authorize")
	public ResponseEntity<String> getAuthorizationUrl() {

		String scopes = URLEncoder.encode("crm.objects.contacts.write crm.objects.contacts.read oauth",
				StandardCharsets.UTF_8);
		String authUrl = "https://app.hubspot.com/oauth/authorize" + "?client_id=" + clientId + "&redirect_uri="
				+ redirectUri + "&scope=" + scopes + "&response_type=code";

		return ResponseEntity.ok(authUrl);
	}

}
