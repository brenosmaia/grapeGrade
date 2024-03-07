package com.brenosmaia.grapegrade.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
		return "Welcome to GrapeGrade!";
	}
}
