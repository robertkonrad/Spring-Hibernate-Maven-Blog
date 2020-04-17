package com.robertkonrad.blog.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.robertkonrad.blog.entity.Tag;
import com.robertkonrad.blog.entity.User;
import org.springframework.web.multipart.MultipartFile;

import com.robertkonrad.blog.entity.Post;

public interface PostService {
	public void deletePost(int postId);

	public void savePost(Post post, MultipartFile file) throws IOException;

	public Post getPost(int postId);

	public List<Post> getPostsByPage(int page, int postsOnOnePage);

	public int getNumberOfAllPosts();

	public List<Post> getPostsByPageAndSearch(int page, int postsOnOnePage, String q);

	public int getNumberOfAllSearchedPosts(String q);

    public List<Tag> getTags();

    public List<Tag> getPostTags(int postId);
}
