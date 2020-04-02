package com.robertkonrad.blog.controller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.robertkonrad.blog.entity.*;
import com.robertkonrad.blog.service.UserService;
import com.robertkonrad.blog.validation.UserMatchesPassword;
import com.robertkonrad.blog.validation.UserPassword;
import com.robertkonrad.blog.validation.UserPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.robertkonrad.blog.service.CommentService;
import com.robertkonrad.blog.service.PostService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class BlogController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/")
	public String index() {
		return "redirect:/page/1";
	}
	
	@RequestMapping(value="/page/{page}")
	public String listPostsByPage(@PathVariable int page, Model theModel) {
		int postsOnOnePage = 10;
		List<Post> posts = postService.getPostsByPage(page, postsOnOnePage);
		int pages = (int) Math.ceil((double)postService.getNumberOfAllPosts() / postsOnOnePage);
		if (pages == 0) {
			pages++;
		}
		theModel.addAttribute("posts", posts);
		theModel.addAttribute("pages", pages);
		return "list_posts";
	}
	
	@RequestMapping(value="/post/{postId}")
	public String detailsPost(@PathVariable int postId, Model theModel) {
		Post post = postService.getPost(postId);
		List<Comment> comments = commentService.getComments(postId);
		theModel.addAttribute("post", post);
		theModel.addAttribute("comments", comments);
		Comment comment = new Comment();
		theModel.addAttribute("comment", comment);
		return "post_details";
	}
	
	@RequestMapping(value="/post/{postId}/comment/add")
	public String saveComment(@Valid @ModelAttribute("comment") Comment comment, BindingResult theBindingResult, @PathVariable int postId, Model theModel) {
		if (theBindingResult.hasErrors()){
			Post post = postService.getPost(postId);
			List<Comment> comments = commentService.getComments(postId);
			theModel.addAttribute("post", post);
			theModel.addAttribute("comments", comments);
			theModel.addAttribute("comment", comment);
			return "post_details";
		} else {
			commentService.saveComment(postId, comment);
			return "redirect:/post/{postId}";
		}
	}
	
	@RequestMapping(value="/post/{postId}/delete")
	public String deletePost(@PathVariable int postId) {
		postService.deletePost(postId);
		return "redirect:/";
	}
	
	@RequestMapping(value="/post/form")
	public String formPost(Model theModel) {
		Post post = new Post();
		theModel.addAttribute("post", post);
		return "post-form";
	}
	
	@RequestMapping(value="/post/save")
	public String savePost(@Valid @ModelAttribute("post") Post post, BindingResult theBindingResult, @RequestParam("file") MultipartFile file) throws IOException {
		if (theBindingResult.hasErrors()){
			return "post-form";
		} else {
			postService.savePost(post, file);
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/post/{postId}/update")
	public String updatePost(Model theModel, @PathVariable int postId) {
		Post post = postService.getPost(postId);
		theModel.addAttribute("post", post);
		return "post-form";
	}

	@RequestMapping(value="/admin/users")
	public String listUsers(Model theModel) {
		List<List> result = userService.getUsers();
		List<User> users = result.get(0);
		List<String> auth = result.get(1);
		theModel.addAttribute("users", users);
		theModel.addAttribute("auth", auth);
		return "list_users";
	}

	@RequestMapping(value="/admin/users/{username}/delete")
	public String deleteUser(@PathVariable String username) {
		userService.deleteUser(username);
		return "redirect:/";
	}

	@RequestMapping(value="/admin/users/{username}/edit")
	public String updateUserPassword(Model theModel, @PathVariable String username) {
		User user = userService.getUser(username);
		theModel.addAttribute("user", user);
		return "user-form";
	}

	@RequestMapping(value = "/admin/users/{username}/edit/save")
	public String saveUpdatedUserPassword(@Validated({Group2.class}) @ModelAttribute("user") User user, BindingResult theBindingResult, @PathVariable String username) {
		if (theBindingResult.hasErrors()){
			return "user-form";
		} else {
			userService.saveUpdatedUserPassword(user, username);
			return "redirect:/admin/users";
		}
	}

	@RequestMapping(value = "/admin/users/{username}/changeRole")
	public String changeUserRole(@PathVariable String username, Model theModel){
		String userRole = userService.getUserRole(username);
		String[] roleTypes = {"USER", "ADMIN"};
		theModel.addAttribute("roleTypes", roleTypes);
		theModel.addAttribute("userRole", userRole);
		return "user-role-form";
	}

	@RequestMapping(value = "/admin/users/{username}/changeRole/save")
	public String saveChangedUserRole(@PathVariable String username, @RequestParam("role") String role) {
		String userRole = userService.getUserRole(username);
		if (!role.equals(userRole)){
			userService.saveChangedUserRole(username, role);
			if (userRole.equals("ADMIN")){
				SecurityContextHolder.clearContext();
			}
		}
		return "redirect:/admin/users";
		}
}
