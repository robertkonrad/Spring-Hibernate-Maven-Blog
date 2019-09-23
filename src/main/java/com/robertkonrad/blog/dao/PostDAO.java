package com.robertkonrad.blog.dao;

import java.util.List;

import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.entity.User;

public interface PostDAO {
	public void deletePost(int postId);

	public void savePost(Post post);

	public Post getPost(int postId);

	public void saveUser(User user);

	public List<Post> getPostsByPage(int page, int postsOnOnePage);

	public int getNumberOfAllPosts();
}
