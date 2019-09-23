package com.robertkonrad.blog.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robertkonrad.blog.dao.PostDAO;
import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.entity.User;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO postDAO;

	@Override
	public void deletePost(int postId) {
		postDAO.deletePost(postId);
	}

	@Override
	public void savePost(Post post) {
		postDAO.savePost(post);
	}

	@Override
	public Post getPost(int postId) {
		return postDAO.getPost(postId);
	}

	@Override
	public void saveUser(User user) {
		postDAO.saveUser(user);
	}

	@Override
	public List<Post> getPostsByPage(int page, int postsOnOnePage) {
		return postDAO.getPostsByPage(page, postsOnOnePage);
	}

	@Override
	public int getNumberOfAllPosts() {
		return postDAO.getNumberOfAllPosts();
	}

}
