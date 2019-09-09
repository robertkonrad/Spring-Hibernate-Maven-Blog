package com.robertkonrad.blog.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.util.HibernateUtil;

@Repository
public class PostDAOImpl implements PostDAO {

	@Override
	public List<Post> getPosts() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		List<Post> posts = session.createQuery("FROM Post", Post.class).getResultList();
		
//		session.getTransaction().commit();
		HibernateUtil.shutdown();
		return posts;
	}

}
