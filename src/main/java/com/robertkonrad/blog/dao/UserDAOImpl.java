package com.robertkonrad.blog.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.robertkonrad.blog.entity.Role;
import com.robertkonrad.blog.entity.User;

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

}
