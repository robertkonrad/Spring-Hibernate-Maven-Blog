package com.robertkonrad.blog.controller;

import java.io.IOException;
import java.util.List;

import com.robertkonrad.blog.entity.User;
import com.robertkonrad.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.robertkonrad.blog.entity.Comment;
import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.service.CommentService;
import com.robertkonrad.blog.service.PostService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String saveComment(@Valid @ModelAttribute("comment") Comment comment, BindingResult theBindingResult, @PathVariable int postId) {
		if (theBindingResult.hasErrors()){
			// todo
			return "";
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
}
