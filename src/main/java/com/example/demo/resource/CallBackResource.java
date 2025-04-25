package com.example.demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OAuthService;

@RestController
@RequestMapping("/oauth")
public class CallBackResource {

	@Autowired
	private OAuthService oAuthService;

	@GetMapping("/callback")
	public ResponseEntity<String> handleCallback(@RequestParam String code) {

		ResponseEntity<String> token = oAuthService.exchangeCodeForToken(code);
		return ResponseEntity.ok("Access Token: " + token);
	}

}
