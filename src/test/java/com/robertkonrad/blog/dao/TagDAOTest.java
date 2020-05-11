package com.robertkonrad.blog.dao;

import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.entity.Tag;
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
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TagDAOTest {

    @Autowired
    private TagDAO tagDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PostDAO postDAO;

    @Transactional
    @Test
    @Rollback
    public void saveTagTest() {
        Tag tag = new Tag("__tagtest212xxx__");
        int tagsBefore = tagDAO.getTags().size();
        tagDAO.saveTag(tag);
        Assert.assertEquals(tagsBefore + 1, tagDAO.getTags().size());
        Assert.assertEquals(tag.getTag(), tagDAO.getTag(tag.getTag()).getTag());
    }

    @Transactional
    @Test
    @Rollback
    public void getTagTest() {
        Tag tag = new Tag("__tagtest212xxx__");
        tagDAO.saveTag(tag);
        Assert.assertEquals(tag.getTag(), tagDAO.getTag(tag.getTag()).getTag());
    }

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void deleteTagTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Post post = new Post("title", "desc", userDAO.getUser(user.getUsername()));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt",
                "Test".getBytes());
        int postId = postDAO.savePost(post, mockMultipartFile);
        Tag tag1 = new Tag("__tagtest212xxx__1");
        tagDAO.saveTag(tag1);
        Tag tag2 = new Tag("__tagtest212xxx__2");
        tagDAO.saveTag(tag2);
        List<String> tagsList = Arrays.asList("__tagtest212xxx__1", "__tagtest212xxx__2");
        tagDAO.savePostTags(postId, tagsList);
        Assert.assertEquals(2, tagDAO.getPostTags(postId).size());
        tagDAO.deleteTag("__tagtest212xxx__1");
        Assert.assertEquals(null, tagDAO.getTag("__tagtest212xxx__1"));
        Assert.assertEquals(1, tagDAO.getPostTags(postId).size());
    }

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void savePostTagsTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Post post = new Post("title", "desc", userDAO.getUser(user.getUsername()));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt",
                "Test".getBytes());
        int postId = postDAO.savePost(post, mockMultipartFile);
        Tag tag1 = new Tag("__tagtest212xxx__1");
        tagDAO.saveTag(tag1);
        Tag tag2 = new Tag("__tagtest212xxx__2");
        tagDAO.saveTag(tag2);
        List<String> tagsList = Arrays.asList("__tagtest212xxx__1", "__tagtest212xxx__2");
        Assert.assertEquals(0, tagDAO.getPostTags(postId).size());
        tagDAO.savePostTags(postId, tagsList);
        Assert.assertEquals(2, tagDAO.getPostTags(postId).size());
    }

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void getTagsTest() {
        int tagsBefore = tagDAO.getTags().size();
        Tag tag1 = new Tag("__tagtest212xxx__1");
        tagDAO.saveTag(tag1);
        Tag tag2 = new Tag("__tagtest212xxx__2");
        tagDAO.saveTag(tag2);
        Assert.assertEquals(tagsBefore + 2, tagDAO.getTags().size());
    }

    @Transactional
    @Test
    @Rollback
    @WithMockUser(username = "__ss767test6667ss__")
    public void getPostTagsTest() {
        User user = new User("__ss767test6667ss__", "password", 1);
        userDAO.saveUser(user);
        Post post = new Post("title", "desc", userDAO.getUser(user.getUsername()));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.txt",
                "Test".getBytes());
        int postId = postDAO.savePost(post, mockMultipartFile);
        Tag tag1 = new Tag("__tagtest212xxx__1");
        tagDAO.saveTag(tag1);
        Tag tag2 = new Tag("__tagtest212xxx__2");
        tagDAO.saveTag(tag2);
        int postTagsBefore = tagDAO.getPostTags(postId).size();
        List<String> tagsList = Arrays.asList("__tagtest212xxx__1", "__tagtest212xxx__2");
        tagDAO.savePostTags(postId, tagsList);
        Assert.assertEquals(postTagsBefore + 2, tagDAO.getPostTags(postId).size());
    }
}
