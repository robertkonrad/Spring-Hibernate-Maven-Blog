package com.robertkonrad.blog.service;

import java.util.List;

import com.robertkonrad.blog.entity.Post;

public interface PostService {
	public void deletePost(int postId);

	public void savePost(Post post);

	public Post getPost(int postId);

	public List<Post> getPostsByPage(int page, int postsOnOnePage);

	public int getNumberOfAllPosts();
}
