package com.robertkonrad.blog.service;

import com.robertkonrad.blog.entity.User;

import java.util.List;

public interface UserService {
	public void saveUser(User user);

	public boolean usernameAvailable(String username);

    public List<List> getUsers();

    public void deleteUser(String username);

    public User getUser(String username);

    public void saveUpdatedUserPassword(User user, String username);

    public String getUserRole(String username);

    public void saveChangedUserRole(String username, String role);
}
