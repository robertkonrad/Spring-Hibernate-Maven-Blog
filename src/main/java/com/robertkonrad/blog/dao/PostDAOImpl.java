package com.robertkonrad.blog.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.entity.Role;
import com.robertkonrad.blog.entity.User;

@Repository
public class PostDAOImpl implements PostDAO {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private EntityManager entityManager;
	
	@Autowired
	public PostDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Transactional
	@Override
	public void deletePost(int postId) {
		Session session = entityManager.unwrap(Session.class);

		Post post = session.get(Post.class, postId);
		session.delete(post);	
	}

	@Transactional
	@Override
	public void savePost(Post post) {
		Session session = entityManager.unwrap(Session.class);
		
		if (post.getId() == 0) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = session.createQuery("FROM User WHERE username='"+auth.getName()+"'", User.class).getSingleResult();
			
			Date createdDate = new Date();
			post.setCreatedDate(createdDate);
			post.setLastModificated(createdDate);
			post.setAuthor(user);
		} else {
			Post orginalPost = getPost(post.getId());
			post.setAuthor(orginalPost.getAuthor());
			post.setCreatedDate(orginalPost.getCreatedDate());
			Date lastModificated = new Date();
			post.setLastModificated(lastModificated);
		}
		
		session.merge(post);

	}

	@Transactional
	@Override
	public Post getPost(int postId) {
		Session session = entityManager.unwrap(Session.class);
		
		Post post = session.get(Post.class, postId);
		
		return post;
	}

	@Transactional
	@Override
	public void saveUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Role role = new Role();
		role.setUser(user);
		
		session.save(user);
		session.save(role);	
	}

	@Transactional
	@Override
	public List<Post> getPostsByPage(int page, int postsOnOnePage) {
		
		Session session = entityManager.unwrap(Session.class);
		
		int minRowNum;

		if (page == 1) {
			minRowNum = 0;
		} else {
			minRowNum = (page - 1) * 10;
		}
		
		List<Post> posts = session.createQuery("FROM Post", Post.class)
				.setFirstResult(minRowNum).setMaxResults(postsOnOnePage)
				.getResultList();
		
		return posts;
	}

	@Transactional
	@Override
	public int getNumberOfAllPosts() {
		Session session = entityManager.unwrap(Session.class);
		
		List<Post> posts = session.createQuery("FROM Post", Post.class).getResultList();
		int numberOfAllPosts = posts.size();
		
		return numberOfAllPosts;
	}

}
