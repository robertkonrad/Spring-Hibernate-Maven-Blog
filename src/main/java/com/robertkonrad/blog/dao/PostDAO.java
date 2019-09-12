package com.robertkonrad.blog.dao;

import java.util.List;

import com.robertkonrad.blog.entity.Post;

public interface PostDAO {
	public List<Post> getPosts();

	public void deletePost(int postId);

	public void savePost(Post post);

	public Post getPost(int postId);
}
