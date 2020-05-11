package com.robertkonrad.blog.dao;

import com.robertkonrad.blog.entity.Comment;
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
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CommentDAOTest {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private UserDAO userDAO;

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void getCommentsTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Post post = new Post("title", "desc", userDAO.getUser(user.getUsername()));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt",
                "Test".getBytes());
        int postId = postDAO.savePost(post, mockMultipartFile);
        List<Comment> commentsBefore = commentDAO.getComments(postId);
        Comment comment1 = new Comment("123");
        commentDAO.saveComment(postId, comment1);
        Comment comment2 = new Comment("456");
        commentDAO.saveComment(postId, comment2);
        List<Comment> commentsAfter = commentDAO.getComments(postId);
        Assert.assertEquals(commentsBefore.size() + 2, commentsAfter.size());
    }

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void saveCommentTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Post post = new Post("title", "desc", userDAO.getUser(user.getUsername()));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt",
                "Test".getBytes());
        int postId = postDAO.savePost(post, mockMultipartFile);
        Comment comment1 = new Comment("123");
        commentDAO.saveComment(postId, comment1);
        Assert.assertNotEquals(0, comment1.getId());
    }

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void deleteCommentTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Post post = new Post("title", "desc", userDAO.getUser(user.getUsername()));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt",
                "Test".getBytes());
        int postId = postDAO.savePost(post, mockMultipartFile);
        Comment comment1 = new Comment("123");
        commentDAO.saveComment(postId, comment1);
        Assert.assertEquals(1, commentDAO.getComments(postId).size());
        commentDAO.deleteComment(comment1.getId());
        Assert.assertEquals(0, commentDAO.getComments(postId).size());
    }
}
