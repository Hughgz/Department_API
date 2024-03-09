package com.minhhieu.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
	@GetMapping("/hello")
	public String hi() {
		// get mapping vào 1 hàm, trả về tên file html của views
		return "hello.html";
	}
}
