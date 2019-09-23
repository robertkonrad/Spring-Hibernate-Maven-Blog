package com.robertkonrad.blog.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.entity.Role;
import com.robertkonrad.blog.entity.User;
import com.robertkonrad.blog.util.HibernateUtil;

@Repository
public class PostDAOImpl implements PostDAO {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void deletePost(int postId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Post post = session.get(Post.class, postId);
		session.delete(post);
		
		session.getTransaction().commit();
	}

	@Override
	public void savePost(Post post) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		
		if (post.getId() == 0) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			Date createdDate = new Date();
			post.setCreatedDate(createdDate);
			post.setLastModificated(createdDate);
			post.setAuthor(auth.getName());
		} else {
			Post orginalPost = getPost(post.getId());
			post.setAuthor(orginalPost.getAuthor());
			post.setCreatedDate(orginalPost.getCreatedDate());
			Date lastModificated = new Date();
			post.setLastModificated(lastModificated);
		}
		
		session.saveOrUpdate(post);
		
		session.getTransaction().commit();
	}

	@Override
	public Post getPost(int postId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Post post = session.get(Post.class, postId);
		
		session.getTransaction().commit();
		
		return post;
	}

	@Override
	public void saveUser(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(1);
		
		Role role = new Role();
		role.setAuthority("USER");
		role.setUsername(user.getUsername());
		
		session.save(user);
		session.save(role);
		
		session.getTransaction().commit();
	}

	@Override
	public List<Post> getPostsByPage(int page, int postsOnOnePage) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		int minRowNum;
		
		if (page == 1) {
			minRowNum = 0;
		} else {
			minRowNum = (page - 1) * 10;
		}
		
		List<Post> posts = session.createQuery("FROM Post", Post.class)
				.setFirstResult(minRowNum).setMaxResults(postsOnOnePage)
				.getResultList();
		
		session.getTransaction().commit();
		
		return posts;
	}

	@Override
	public int getNumberOfAllPosts() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		List<Post> posts = session.createQuery("FROM Post", Post.class).getResultList();
		int numberOfAllPosts = posts.size();
		
		session.getTransaction().commit();
		
		return numberOfAllPosts;
	}

}
