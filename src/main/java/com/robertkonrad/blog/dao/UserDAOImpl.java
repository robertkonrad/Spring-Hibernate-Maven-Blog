package com.robertkonrad.blog.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.robertkonrad.blog.entity.Role;
import com.robertkonrad.blog.entity.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void saveUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Role role = new Role();
		role.setUser(user);
		
		session.save(user);
		session.save(role);	
	}

	@Override
	public boolean usernameAvailable(String username) {
		Session session = entityManager.unwrap(Session.class);

		User user = session.get(User.class, username);

		if (user == null){
			return true;
		}

		return false;
	}

	@Override
	public List<List> getUsers() {
		Session session = entityManager.unwrap(Session.class);
		List<String> auth = new ArrayList<>();

		List<User> users = session.createQuery("FROM User", User.class).getResultList();

		for(User user : users){
			String role = session.createQuery("SELECT authority FROM Role WHERE username='" + user.getUsername() + "'", String.class).getSingleResult();
			auth.add(role);
		}

		List<List> result = new ArrayList<List>();
		result.add(users);
		result.add(auth);

		return result;
	}

	@Override
	public void deleteUser(String username) {
		Session session = entityManager.unwrap(Session.class);

		User user = session.createQuery("FROM User WHERE username='" + username + "'", User.class).getSingleResult();
		Role auth = session.createQuery("FROM Role WHERE username='" + username + "'", Role.class).getSingleResult();

		session.delete(auth);
		session.delete(user);
	}

	@Override
	public User getUser(String username) {
		Session session = entityManager.unwrap(Session.class);

		User user = session.createQuery("FROM User WHERE username='" + username +"'", User.class).getSingleResult();

		return user;
	}

	@Override
	public void saveUpdatedUserPassword(User user, String username) {
		Session session = entityManager.unwrap(Session.class);

		User eUser = session.createQuery("FROM User WHERE username='" + username + "'", User.class).getSingleResult();

		eUser.setPassword(passwordEncoder.encode(user.getPassword()));

		session.update(eUser);

	}

}
