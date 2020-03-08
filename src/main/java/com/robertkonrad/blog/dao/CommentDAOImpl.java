package com.robertkonrad.blog.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.robertkonrad.blog.entity.Comment;
import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.entity.User;

@Repository
public class CommentDAOImpl implements CommentDAO {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Comment> getComments(int postId) {
		Session session = entityManager.unwrap(Session.class);

		List<Comment> comments = session.createQuery("FROM Comment C WHERE C.post.id='"+postId+"'", Comment.class).getResultList();
		
		return comments;
	}


	@Override
	public void saveComment(int postId, Comment comment) {
		Session session = entityManager.unwrap(Session.class);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = session.createQuery("FROM User U WHERE U.username='"+auth.getName()+"'", User.class).getSingleResult();
		Post post = session.createQuery("FROM Post P WHERE P.id='"+postId+"'", Post.class).getSingleResult();

		comment.setAuthor(user);
		comment.setPost(post);
		
		Date createdDate = new Date();
		comment.setCreatedDate(createdDate);
		comment.setLastModificated(createdDate);
		
		session.save(comment);
	}

}
