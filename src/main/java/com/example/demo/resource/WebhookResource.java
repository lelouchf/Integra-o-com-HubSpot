package com.example.demo.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/webhook")
public class WebhookResource {

	@PostMapping("/contact-creation")
	public ResponseEntity<Void> handleContactCreation(@RequestBody String payload) {
		System.out.println("Recebido Webhook de Contact Creation:");
		System.out.println(payload);

		return ResponseEntity.ok().build();
	}
}
