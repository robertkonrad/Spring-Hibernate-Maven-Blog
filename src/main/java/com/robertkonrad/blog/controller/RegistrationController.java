package com.robertkonrad.blog.controller;

import com.robertkonrad.blog.entity.Group1;
import com.robertkonrad.blog.entity.Group2;
import com.robertkonrad.blog.entity.User;
import com.robertkonrad.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
	
	@Autowired
	private UserService userService;

	@RequestMapping("/user/form")
	public String register_form(Model theModel) {
		User user = new User();
		theModel.addAttribute("user", user);
		return "register-form";
	}
	
	@RequestMapping("/user/save")
	public String save_user(@Validated({Group1.class, Group2.class}) @ModelAttribute("user") User user, BindingResult theBindingResult) {
		if (theBindingResult.hasErrors()){
			return "register-form";
		} else {
			userService.saveUser(user);
			return "redirect:/login";
		}
	}
	
	
}
