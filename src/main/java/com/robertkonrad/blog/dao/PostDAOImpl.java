package com.robertkonrad.blog.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.entity.User;

@Repository
public class PostDAOImpl implements PostDAO {
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	ServletContext context;

	@Override
	public void deletePost(int postId) {
		Session session = entityManager.unwrap(Session.class);
		
		Post post = session.get(Post.class, postId);
		
		String folder = context.getRealPath("/image/");
		
		File file = new File(folder + post.getImage());
		file.delete();
		
		session.delete(post);	
	}

	@Override
	public void savePost(Post post, MultipartFile file) {
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
		
		
		String folder = context.getRealPath("/image/");
		Path path = Paths.get(folder + post.getTitle() + "-" + file.getOriginalFilename());
		try {
			file.transferTo(path);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		post.setImage(post.getTitle() + "-" + file.getOriginalFilename());
		
		session.merge(post);

	}

	@Override
	public Post getPost(int postId) {
		Session session = entityManager.unwrap(Session.class);
		
		Post post = session.get(Post.class, postId);
		
		return post;
	}

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

	@Override
	public int getNumberOfAllPosts() {
		Session session = entityManager.unwrap(Session.class);
		
		List<Post> posts = session.createQuery("FROM Post", Post.class).getResultList();
		int numberOfAllPosts = posts.size();
		
		return numberOfAllPosts;
	}

}
