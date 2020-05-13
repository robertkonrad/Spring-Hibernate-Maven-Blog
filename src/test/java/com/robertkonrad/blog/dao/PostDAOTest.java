package com.robertkonrad.blog.dao;

import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PostDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PostDAO postDAO;

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void savePostTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Post post = new Post("title", "desc", userDAO.getUser(user.getUsername()));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt",
                "Test".getBytes());
        int postId = postDAO.savePost(post, mockMultipartFile);
        Assert.assertNotEquals(0, postId);
    }

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void getPostTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Post post = new Post("title", "desc", userDAO.getUser(user.getUsername()));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt",
                "Test".getBytes());
        int postId = postDAO.savePost(post, mockMultipartFile);
        Post post1 = postDAO.getPost(postId);
        Assert.assertEquals(postId, post1.getId());
    }

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void getNumberOfAllPostsTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Post post = new Post("title", "desc", userDAO.getUser(user.getUsername()));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt",
                "Test".getBytes());
        int numberOfAllPostsBefore = postDAO.getNumberOfAllPosts();
        int postId = postDAO.savePost(post, mockMultipartFile);
        int numberOfAllPostsAfter = postDAO.getNumberOfAllPosts();
        Assert.assertEquals(numberOfAllPostsBefore + 1, numberOfAllPostsAfter);
    }

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void deletePostTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Post post = new Post("title", "desc", userDAO.getUser(user.getUsername()));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt",
                "Test".getBytes());
        int postId = postDAO.savePost(post, mockMultipartFile);
        int numberOfAllPostsBefore = postDAO.getNumberOfAllPosts();
        postDAO.deletePost(postId);
        int numberOfAllPostsAfter = postDAO.getNumberOfAllPosts();
        Assert.assertEquals(numberOfAllPostsBefore - 1, numberOfAllPostsAfter);
        Assert.assertEquals(null, postDAO.getPost(postId));
    }

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void getPostsByPageTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        for (int i = 1; i < 11; i++) {
            Post post = new Post("title", "desc", userDAO.getUser(user.getUsername()));
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "test.txt",
                    "Test".getBytes());
            int postId = postDAO.savePost(post, mockMultipartFile);
        }
        Assert.assertEquals(5, postDAO.getPostsByPage(1, 5).size());
        Assert.assertEquals(5, postDAO.getPostsByPage(2, 5).size());
        Assert.assertNotEquals(postDAO.getPostsByPage(1, 5), postDAO.getPostsByPage(2, 5));
    }

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void getPostsByPageAndSearchTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        for (int i = 1; i < 11; i++) {
            Post post = new Post("__656title656___", "desc", userDAO.getUser(user.getUsername()));
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "test.txt",
                    "Test".getBytes());
            int postId = postDAO.savePost(post, mockMultipartFile);
        }
        Assert.assertEquals(10, postDAO.getPostsByPageAndSearch(1, 10, "__656title656___").size());
        Assert.assertEquals(0, postDAO.getPostsByPageAndSearch(1, 10, "__656title656___ss").size());
    }

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void getNumberOfAllSearchedPostsTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        for (int i = 1; i < 11; i++) {
            Post post = new Post("__656title656___", "desc", userDAO.getUser(user.getUsername()));
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "test.txt",
                    "Test".getBytes());
            int postId = postDAO.savePost(post, mockMultipartFile);
        }
        Assert.assertEquals(10, postDAO.getNumberOfAllSearchedPosts("__656title656___"));
        Assert.assertEquals(0, postDAO.getNumberOfAllSearchedPosts("__656title656___ss"));
    }
}
