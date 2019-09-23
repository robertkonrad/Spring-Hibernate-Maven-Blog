package com.robertkonrad.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.service.PostService;

@Controller
public class BlogController {
	
	@Autowired
	private PostService postService;
	
	@RequestMapping(value="/")
	public String listPosts(Model theModel) {
		int postsOnOnePage = 10;
		List<Post> posts = postService.getPostsByPage(1, postsOnOnePage);
		int pages = (int) Math.ceil((double)postService.getNumberOfAllPosts() / postsOnOnePage);
		theModel.addAttribute("posts", posts);
		theModel.addAttribute("pages", pages);
		return "list_posts";
	}
	
	@RequestMapping(value="/{page}")
	public String listPostsByPage(@PathVariable int page, Model theModel) {
		int postsOnOnePage = 10;
		List<Post> posts = postService.getPostsByPage(page, postsOnOnePage);
		int pages = (int) Math.ceil((double)postService.getNumberOfAllPosts() / postsOnOnePage);
		theModel.addAttribute("posts", posts);
		theModel.addAttribute("pages", pages);
		return "list_posts";
	}
	
	@RequestMapping(value="/deletePost")
	public String deletePost(@RequestParam("postId") int postId) {
		postService.deletePost(postId);
		return "redirect:/";
	}
	
	@RequestMapping(value="/formPost")
	public String formPost(Model theModel) {
		Post post = new Post();
		theModel.addAttribute("post", post);
		return "post-form";
	}
	
	@RequestMapping(value="/savePost")
	public String savePost(@ModelAttribute("post") Post post) {
		postService.savePost(post);
		return "redirect:/";
	}
	
	@RequestMapping(value="/updatePost")
	public String updatePost(Model theModel, @RequestParam("postId") int postId) {
		Post post = postService.getPost(postId);
		theModel.addAttribute("post", post);
		return "post-form";
	}
}
