package com.robertkonrad.blog.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import com.robertkonrad.blog.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.robertkonrad.blog.dao.PostDAO;
import com.robertkonrad.blog.entity.Post;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO postDAO;

	@Transactional
	@Override
	public void deletePost(int postId) {
		postDAO.deletePost(postId);
	}

	@Transactional
	@Override
	public void savePost(Post post, MultipartFile file) throws IOException {
		postDAO.savePost(post, file);
	}

	@Transactional
	@Override
	public Post getPost(int postId) {
		return postDAO.getPost(postId);
	}

	@Transactional
	@Override
	public List<Post> getPostsByPage(int page, int postsOnOnePage) {
		return postDAO.getPostsByPage(page, postsOnOnePage);
	}

	@Transactional
	@Override
	public int getNumberOfAllPosts() {
		return postDAO.getNumberOfAllPosts();
	}

	@Transactional
	@Override
	public List<Post> getPostsByPageAndSearch(int page, int postsOnOnePage, String q) {
		return postDAO.getPostsByPageAndSearch(page, postsOnOnePage, q);
	}

	@Transactional
	@Override
	public int getNumberOfAllSearchedPosts(String q) {
		return postDAO.getNumberOfAllSearchedPosts(q);
	}

	@Transactional
	@Override
	public List<Tag> getTags() {
		return postDAO.getTags();
	}

	@Transactional
	@Override
	public List<Tag> getPostTags(int postId) {
		return postDAO.getPostTags(postId);
	}

}
