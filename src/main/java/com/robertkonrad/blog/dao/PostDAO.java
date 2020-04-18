package com.robertkonrad.blog.dao;

import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.entity.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostDAO {
    public void deletePost(int postId);

    public int savePost(Post post, MultipartFile file);

    public Post getPost(int postId);

    public List<Post> getPostsByPage(int page, int postsOnOnePage);

    public int getNumberOfAllPosts();

    public List<Post> getPostsByPageAndSearch(int page, int postsOnOnePage, String q);

    public int getNumberOfAllSearchedPosts(String q);

    public List<Tag> getTags();

    public List<Tag> getPostTags(int postId);

    public void savePostTags(int postId, List<String> tags);
}
