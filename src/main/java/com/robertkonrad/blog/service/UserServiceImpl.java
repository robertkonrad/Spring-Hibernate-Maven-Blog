package com.robertkonrad.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robertkonrad.blog.dao.UserDAO;
import com.robertkonrad.blog.entity.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;

	@Transactional
	@Override
	public void saveUser(User user) {
		userDAO.saveUser(user);
	}

	@Transactional
	@Override
	public boolean usernameAvailable(String username) {
		return userDAO.usernameAvailable(username);
	}

	@Override
	public List<List> getUsers() {
		return userDAO.getUsers();
	}

}
