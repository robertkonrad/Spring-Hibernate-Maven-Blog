package com.robertkonrad.blog.dao;

import com.robertkonrad.blog.entity.User;

public interface UserDAO {
	public void saveUser(User user);

	public boolean usernameAvailable(String username);
}
