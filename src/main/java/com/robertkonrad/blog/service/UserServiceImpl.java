package com.robertkonrad.blog.service;

import com.robertkonrad.blog.dao.UserDAO;
import com.robertkonrad.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    @Override
    public List<List> getUsers() {
        return userDAO.getUsers();
    }

    @Transactional
    @Override
    public void deleteUser(String username) {
        userDAO.deleteUser(username);
    }

    @Transactional
    @Override
    public User getUser(String username) {
        return userDAO.getUser(username);
    }

    @Transactional
    @Override
    public void saveUpdatedUserPassword(User user, String username) {
        userDAO.saveUpdatedUserPassword(user, username);
    }

    @Transactional
    @Override
    public String getUserRole(String username) {
        return userDAO.getUserRole(username);
    }

    @Transactional
    @Override
    public void saveChangedUserRole(String username, String role) {
        userDAO.saveChangedUserRole(username, role);
    }

}
