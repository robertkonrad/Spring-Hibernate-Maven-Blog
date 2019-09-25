package com.robertkonrad.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.robertkonrad.blog.entity.User;
import com.robertkonrad.blog.service.PostService;

@Controller
public class RegistrationController {
	
	@Autowired
	private PostService postService;

	@RequestMapping("/user/form")
	public String register_form(Model theModel) {
		User user = new User();
		theModel.addAttribute("user", user);
		return "register-form";
	}
	
	@RequestMapping("/user/save")
	public String save_user(@ModelAttribute("user") User user) {
		postService.saveUser(user);
		return "redirect:/login";
	}
	
	
}
