package com.robertkonrad.blog.controller;

import com.robertkonrad.blog.entity.*;
import com.robertkonrad.blog.service.CommentService;
import com.robertkonrad.blog.service.PostService;
import com.robertkonrad.blog.service.TagService;
import com.robertkonrad.blog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:/page/1";
    }

    @RequestMapping(value = "/page/{page}")
    public String listPostsByPage(@PathVariable int page, Model theModel, @RequestParam(required = false, name = "q") String q) {
        int postsOnOnePage = 10, pages;
        List<Post> posts;
        if ((q == null) || (StringUtils.isBlank(q))) {
            posts = postService.getPostsByPage(page, postsOnOnePage);
            pages = (int) Math.ceil((double) postService.getNumberOfAllPosts() / postsOnOnePage);
        } else {
            posts = postService.getPostsByPageAndSearch(page, postsOnOnePage, q);
            pages = (int) Math.ceil((double) postService.getNumberOfAllSearchedPosts(q) / postsOnOnePage);
        }
        if (pages == 0) {
            pages++;
        }
        List<Tag> tags = tagService.getTags();
        theModel.addAttribute("tags", tags);
        theModel.addAttribute("posts", posts);
        theModel.addAttribute("pages", pages);
        return "list_posts";
    }

    @RequestMapping(value = "/post/{postId}")
    public String detailsPost(@PathVariable int postId, Model theModel) {
        Post post = postService.getPost(postId);
        List<Comment> comments = commentService.getComments(postId);
        Comment comment = new Comment();
        List<Tag> tags = tagService.getPostTags(postId);
        theModel.addAttribute("tags", tags);
        theModel.addAttribute("post", post);
        theModel.addAttribute("comments", comments);
        theModel.addAttribute("comment", comment);
        return "post_details";
    }

    @RequestMapping(value = "/post/{postId}/comment/add")
    public String saveComment(@Valid @ModelAttribute("comment") Comment comment, BindingResult theBindingResult, @PathVariable int postId, Model theModel) {
        if (theBindingResult.hasErrors()) {
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

    @RequestMapping(value = "/post/{postId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable int commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/post/{postId}";
    }

    @RequestMapping(value = "/post/{postId}/delete")
    public String deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
        return "redirect:/";
    }

    @RequestMapping(value = "/post/form")
    public String formPost(Model theModel) {
        Post post = new Post();
        List<Tag> tags = tagService.getTags();
        theModel.addAttribute("post", post);
        theModel.addAttribute("tags", tags);
        return "post-form";
    }

    @RequestMapping(value = "/post/save")
    public String savePost(@Valid @ModelAttribute("post") Post post, BindingResult theBindingResult,
                           @RequestParam("file") MultipartFile file, @RequestParam(value = "tagsCheckbox", required = false, defaultValue = "") List<String> tags) throws IOException {
        if (theBindingResult.hasErrors()) {
            return "post-form";
        } else {
            int postId = postService.savePost(post, file);
            tagService.savePostTags(postId, tags);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/post/{postId}/update")
    public String updatePost(Model theModel, @PathVariable int postId) {
        Post post = postService.getPost(postId);
        List<Tag> tags = tagService.getTags();
        List<Tag> currentTags = tagService.getPostTags(postId);
        theModel.addAttribute("post", post);
        theModel.addAttribute("tags", tags);
        theModel.addAttribute("currentTags", currentTags);
        return "post-form";
    }

    @RequestMapping(value = "/admin/users")
    public String listUsers(Model theModel) {
        List<List> result = userService.getUsers();
        List<User> users = result.get(0);
        List<String> auth = result.get(1);
        theModel.addAttribute("users", users);
        theModel.addAttribute("auth", auth);
        return "list_users";
    }

    @RequestMapping(value = "/admin/users/{username}/delete")
    public String deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return "redirect:/";
    }

    @RequestMapping(value = "/admin/users/{username}/edit")
    public String updateUserPassword(Model theModel, @PathVariable String username) {
        User user = userService.getUser(username);
        theModel.addAttribute("user", user);
        return "user-form";
    }

    @RequestMapping(value = "/admin/users/{username}/edit/save")
    public String saveUpdatedUserPassword(@Validated({Group2.class}) @ModelAttribute("user") User user, BindingResult theBindingResult, @PathVariable String username) {
        if (theBindingResult.hasErrors()) {
            return "user-form";
        } else {
            userService.saveUpdatedUserPassword(user, username);
            return "redirect:/admin/users";
        }
    }

    @RequestMapping(value = "/admin/users/{username}/changeRole")
    public String changeUserRole(@PathVariable String username, Model theModel) {
        String userRole = userService.getUserRole(username);
        String[] roleTypes = {"USER", "ADMIN"};
        theModel.addAttribute("roleTypes", roleTypes);
        theModel.addAttribute("userRole", userRole);
        return "user-role-form";
    }

    @RequestMapping(value = "/admin/users/{username}/changeRole/save")
    public String saveChangedUserRole(@PathVariable String username, @RequestParam("role") String role) {
        String userRole = userService.getUserRole(username);
        if (!role.equals(userRole)) {
            userService.saveChangedUserRole(username, role);
            if (userRole.equals("ADMIN")) {
                SecurityContextHolder.clearContext();
            }
        }
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/tags")
    public String listTags(Model theModel) {
        List<Tag> tags = tagService.getTags();
        theModel.addAttribute("tags", tags);
        return "list_tags";
    }

    @RequestMapping(value = "/admin/tags/{tag}/delete")
    public String deleteTag(@PathVariable String tag) {
        tagService.deleteTag(tag);
        return "redirect:/admin/tags";
    }

    @RequestMapping(value = "/admin/tags/create")
    public String formTag(Model theModel) {
        Tag tag = new Tag();
        theModel.addAttribute("tag", tag);
        return "tag-form";
    }

    @RequestMapping(value = "/admin/tags/create/save")
    public String saveTag(@Valid @ModelAttribute("tag") Tag tag) {
        tagService.saveTag(tag);
        return "redirect:/admin/tags";
    }

}
