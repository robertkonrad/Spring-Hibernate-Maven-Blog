package com.robertkonrad.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.service.PostService;

@Controller
public class BlogController {
	
	@Autowired
	private PostService postService;
	
	@RequestMapping(value="/")
	public String list_posts(Model theModel) {
		List<Post> posts = postService.getPosts();
		theModel.addAttribute("posts", posts);
		return "list_posts";
	}
	
	@RequestMapping(value="/delete_post")
	public String delete_post(@RequestParam("postId") int postId) {
		postService.deletePost(postId);
		return "redirect:/";
	}
	
}
