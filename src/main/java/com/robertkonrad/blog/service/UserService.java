package com.robertkonrad.blog.service;

import com.robertkonrad.blog.entity.User;

import java.util.List;

public interface UserService {
	public void saveUser(User user);

	public boolean usernameAvailable(String username);

    public List<List> getUsers();
}
