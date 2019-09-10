package com.robertkonrad.blog.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robertkonrad.blog.dao.PostDAO;
import com.robertkonrad.blog.entity.Post;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO postDAO;
	
	@Override
	@Transactional
	public List<Post> getPosts() {
		return postDAO.getPosts();
	}

	@Override
	public void deletePost(int postId) {
		postDAO.deletePost(postId);
	}

}
