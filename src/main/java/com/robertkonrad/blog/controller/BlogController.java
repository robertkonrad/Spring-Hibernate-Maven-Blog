package com.robertkonrad.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.service.PostService;

@Controller
public class BlogController {
	
	@Autowired
	private PostService postService;
	
	@RequestMapping(value="/")
	public String index() {
		return "redirect:/page/1";
	}
	
	@GetMapping(value="/page/{page}")
	public String listPostsByPage(@PathVariable int page, Model theModel) {
		int postsOnOnePage = 10;
		List<Post> posts = postService.getPostsByPage(page, postsOnOnePage);
		int pages = (int) Math.ceil((double)postService.getNumberOfAllPosts() / postsOnOnePage);
		theModel.addAttribute("posts", posts);
		theModel.addAttribute("pages", pages);
		return "list_posts";
	}
	
	@RequestMapping(value="/post/delete/{postId}")
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
	public String savePost(@ModelAttribute("post") Post post) {
		postService.savePost(post);
		return "redirect:/";
	}
	
	@RequestMapping(value="/post/update/{postId}")
	public String updatePost(Model theModel, @PathVariable int postId) {
		Post post = postService.getPost(postId);
		theModel.addAttribute("post", post);
		return "post-form";
	}
}
