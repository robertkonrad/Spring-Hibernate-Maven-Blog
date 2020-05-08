package com.robertkonrad.blog.dao;

import com.robertkonrad.blog.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        List<List> usersBefore = userDAO.getUsers();
        User user = new User("__ss767test6667ss__", "password", 1);
        User user2 = new User("__ss767test6667sz__", "password", 1);
        userDAO.saveUser(user);
        userDAO.saveUser(user2);
        List<List> usersAfter = userDAO.getUsers();
        Assert.assertEquals(usersAfter.get(0).size(), usersBefore.get(0).size() + 2);
        Assert.assertEquals(usersBefore.get(0).size(), usersBefore.get(1).size());
        Assert.assertEquals(usersAfter.get(0).size(), usersAfter.get(1).size());
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
    public void getUserRoleTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Assert.assertEquals("USER", userDAO.getUserRole(user.getUsername()));
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

    @Transactional
    @Test
    @Rollback
    public void saveUpdatedUserPasswordTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Assert.assertEquals(true, passwordEncoder.matches("password", userDAO.getUser(user.getUsername()).getPassword()));
        user.setPassword("password2");
        userDAO.saveUpdatedUserPassword(userDAO.getUser(user.getUsername()), user.getUsername());
        Assert.assertNotEquals(true, passwordEncoder.matches("password", userDAO.getUser(user.getUsername()).getPassword()));
        Assert.assertEquals(true, passwordEncoder.matches("password2", userDAO.getUser(user.getUsername()).getPassword()));
    }

    @Transactional
    @Test
    @Rollback
    public void usernameAvailableTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Assert.assertEquals(false, userDAO.usernameAvailable(user.getUsername()));
        User fakeUser = userDAO.getUser("__ss767test6667sz__");
        if (fakeUser == null) {
            Assert.assertEquals(true, userDAO.usernameAvailable("__ss767test6667sz__"));
        }
    }

    @Transactional
    @Test
    @Rollback
    public void deleteUserTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Assert.assertNotEquals(null, userDAO.getUser(user.getUsername()));
        userDAO.deleteUser(user.getUsername());
        Assert.assertEquals(null, userDAO.getUser(user.getUsername()));
    }
}
