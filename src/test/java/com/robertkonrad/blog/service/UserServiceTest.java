package com.robertkonrad.blog.service;

import com.robertkonrad.blog.dao.UserDAO;
import com.robertkonrad.blog.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {

    @Mock
    UserDAO userDAO;

    @InjectMocks
    UserServiceImpl userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private List<User> prepareData() {
        List<User> users = new ArrayList<>();
        User user1 = new User("test1", "password1", 1);
        User user2 = new User("test2", "password2", 1);
        User user3 = new User("test3", "password3", 1);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }

    @Test
    public void getUserTest() {
        Mockito.when(userDAO.getUser("test")).thenReturn(new User("test", "password", 1));
        User user = userService.getUser("test");
        Assert.assertEquals("test", user.getUsername());
        Assert.assertEquals("password", user.getPassword());
        Assert.assertEquals(1, user.getEnabled());
    }

    @Test
    public void getUsersTest() {
        List<String> auth = new ArrayList<>();
        auth.add("role1");
        auth.add("role2");
        auth.add("role3");
        List<List> expected = new ArrayList<>();
        expected.add(prepareData());
        expected.add(auth);
        Mockito.when(userDAO.getUsers()).thenReturn(expected);
        List<List> result = userService.getUsers();
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals(3, result.get(1).size());
    }

    @Test
    public void usernameAvailableTest() {
        Mockito.when(userDAO.getUser("test")).thenReturn(new User("test", "password", 1));
        User user = userService.getUser("test");
        Assert.assertEquals("test", user.getUsername());
    }

    @Test
    public void usernameNotAvailableTest() {
        Mockito.when(userDAO.getUser("test")).thenReturn(null);
        User user = userService.getUser("test");
        Assert.assertNull(user);
    }

    @Test
    public void saveUserTest() {
        List<User> users = prepareData();
        Assert.assertEquals(3, users.size());
        User user = new User("test1", "password", 1);
        users.add(user);
        Assert.assertEquals(4, users.size());
    }
}
