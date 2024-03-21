package com.brenosmaia.grapegrade.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brenosmaia.grapegrade.entity.AuthRequest;
import com.brenosmaia.grapegrade.util.JwtUtil;

@RestController
public class LoginController {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
		return "Welcome to GrapeGrade!";
	}
	
	@RequestMapping(path = "/authenticate", method = RequestMethod.POST)
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
	        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
	        
	        authenticationManager.authenticate(authentication);
			
		} catch (Exception e) {
			throw new Exception("Invalid username or password", e);
		}
		
		return jwtUtil.generateToken(authRequest.getUsername());
	}
}
