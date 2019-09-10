package com.robertkonrad.blog.service;

import java.util.List;

import com.robertkonrad.blog.entity.Post;

public interface PostService {
	public List<Post> getPosts();

	public void deletePost(int postId);
}
