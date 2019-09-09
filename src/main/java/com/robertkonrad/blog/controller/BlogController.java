package com.robertkonrad.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController {
	
	@RequestMapping(value="/")
	public String index() {
		return "index";
	}
	
}
