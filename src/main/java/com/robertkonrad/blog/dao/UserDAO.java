package com.robertkonrad.blog.dao;

import com.robertkonrad.blog.entity.User;

import java.util.List;

public interface UserDAO {
	public void saveUser(User user);

	public boolean usernameAvailable(String username);

    public List<List> getUsers();
}
