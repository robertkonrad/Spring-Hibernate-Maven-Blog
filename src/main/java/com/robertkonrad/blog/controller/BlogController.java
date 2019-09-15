package com.robertkonrad.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	@RequestMapping(value="/deletePost")
	public String delete_post(@RequestParam("postId") int postId) {
		postService.deletePost(postId);
		return "redirect:/";
	}
	
	@RequestMapping(value="/formPost")
	public String form_post(Model theModel) {
		Post post = new Post();
		theModel.addAttribute("post", post);
		return "post-form";
	}
	
	@RequestMapping(value="/savePost")
	public String save_post(@ModelAttribute("post") Post post) {
		postService.savePost(post);
		return "redirect:/";
	}
	
	@RequestMapping(value="/updatePost")
	public String update_post(Model theModel, @RequestParam("postId") int postId) {
		Post post = postService.getPost(postId);
		theModel.addAttribute("post", post);
		return "post-form";
	}
}
