package com.robertkonrad.blog.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.robertkonrad.blog.entity.Post;

public interface PostDAO {
	public void deletePost(int postId);

	public void savePost(Post post, MultipartFile file);

	public Post getPost(int postId);

	public List<Post> getPostsByPage(int page, int postsOnOnePage);

	public int getNumberOfAllPosts();

    public List<Post> getPostsByPageAndSearch(int page, int postsOnOnePage, String q);

	public int getNumberOfAllSearchedPosts(String q);
}
