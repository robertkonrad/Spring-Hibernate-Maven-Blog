package com.robertkonrad.blog.dao;

import com.robertkonrad.blog.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    @Test
    @Rollback
    public void saveUserTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        List<User> users = userDAO.getUsers().get(0);
        Assert.assertEquals(users.get(0).getUsername(), user.getUsername());
        Assert.assertEquals("USER", userDAO.getUserRole(user.getUsername()));
    }

    @Transactional
    @Test
    @Rollback
    public void getUsersTest() {
        List<List> users_before = userDAO.getUsers();
        User user = new User("__ss767test6667ss__", "password", 1);
        User user2 = new User("__ss767test6667sz__", "password", 1);
        userDAO.saveUser(user);
        userDAO.saveUser(user2);
        List<List> users_after = userDAO.getUsers();
        Assert.assertEquals(users_after.get(0).size(), users_before.get(0).size() + 2);
        Assert.assertEquals(users_before.get(0).size(), users_before.get(1).size());
        Assert.assertEquals(users_after.get(0).size(), users_after.get(1).size());
    }

    @Transactional
    @Test
    @Rollback
    public void getUserTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Assert.assertEquals(user, userDAO.getUser(user.getUsername()));
    }

    @Transactional
    @Test
    @Rollback
    public void saveChangedUserRoleTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Assert.assertEquals("USER", userDAO.getUserRole(user.getUsername()));
        userDAO.saveChangedUserRole(user.getUsername(), "ADMIN");
        Assert.assertEquals("ADMIN", userDAO.getUserRole(user.getUsername()));
    }

}
