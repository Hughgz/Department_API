package com.minhhieu.demo.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}
	@PostMapping("/login")
	public String login(@RequestParam("username") String username, 
			@RequestParam("password") String password, 
			HttpSession session) throws IOException {
		if(username.equals("admin") && password.equals("123")) {
			session.setAttribute("username", username);
			return "redirect:/hello";
		}else {
			return "redirect:/login";
		}
		
			
	}
}
