package com.brenosmaia.grapegrade.config;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.brenosmaia.grapegrade.service.LoginService;

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginService loginService;
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginService);
    }
	
	@Bean
	public PasswordEncoder passworEncoder() {
		// Create a map to hold different encoders based on their IDs
        HashMap<String, PasswordEncoder> encoders = new HashMap<>();

        // Add BcryptPasswordEncoder with ID "bcrypt"
        encoders.put("bcrypt", new BCryptPasswordEncoder());

        // Create DelegatingPasswordEncoder with default ID "bcrypt" and map of encoders
        return new DelegatingPasswordEncoder("bcrypt", encoders);
	}
}
