package com.robertkonrad.blog.service;

import com.robertkonrad.blog.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    public void deletePost(int postId);

    public int savePost(Post post, MultipartFile file) throws IOException;

    public Post getPost(int postId);

    public List<Post> getPostsByPage(int page, int postsOnOnePage);

    public int getNumberOfAllPosts();

    public List<Post> getPostsByPageAndSearch(int page, int postsOnOnePage, String q);

    public int getNumberOfAllSearchedPosts(String q);
}
