package com.brenosmaia.grapegrade.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.brenosmaia.grapegrade.entity.AuthRequest;
import com.brenosmaia.grapegrade.util.JwtUtil;

@RestController
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping(path = "/login")
	public String login() {
		return "Welcome to GrapeGrade!";
	}
	
	@PostMapping(path = "/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
	        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
	        
	        authenticationManager.authenticate(authentication);
			
		} catch (Exception e) {
			throw new Exception("Invalid username or password", e);
		}
		
		return JwtUtil.generateToken(authRequest.getUsername());
	}
}
