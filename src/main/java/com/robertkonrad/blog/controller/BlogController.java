package com.robertkonrad.blog.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.robertkonrad.blog.entity.Comment;
import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.service.CommentService;
import com.robertkonrad.blog.service.PostService;

@Controller
public class BlogController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
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
	public String saveComment(@ModelAttribute("comment") Comment comment, @PathVariable int postId) {
		commentService.saveComment(postId, comment);
		return "redirect:/post/{postId}/";
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
	public String savePost(@ModelAttribute("post") Post post, @RequestParam("file") MultipartFile file) {
		try {
			postService.savePost(post, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/post/{postId}/update")
	public String updatePost(Model theModel, @PathVariable int postId) {
		Post post = postService.getPost(postId);
		theModel.addAttribute("post", post);
		return "post-form";
	}
}
