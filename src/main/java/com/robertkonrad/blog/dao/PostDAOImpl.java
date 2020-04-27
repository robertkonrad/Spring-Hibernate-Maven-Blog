package com.robertkonrad.blog.dao;

import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.entity.PostTag;
import com.robertkonrad.blog.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PostDAOImpl implements PostDAO {

    @Autowired
    ServletContext context;
    @Autowired
    private EntityManager entityManager;

    @Override
    public void deletePost(int postId) {
        Session session = entityManager.unwrap(Session.class);
        Post post = session.get(Post.class, postId);
        List<PostTag> postTags = session.createQuery("FROM PostTag pt WHERE pt.post = '" + postId + "'", PostTag.class).getResultList();
        for (PostTag pt : postTags) {
            session.delete(pt);
        }
        if (!post.getImage().equals("")) {
            String folder = context.getRealPath("/image/");
            File file = new File(folder + post.getImage());
            file.delete();
        }
        session.delete(post);
    }

    @Override
    public int savePost(Post post, MultipartFile file) {
        Session session = entityManager.unwrap(Session.class);
        if (post.getId() == 0) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = session.createQuery("FROM User WHERE username='" + auth.getName() + "'", User.class).getSingleResult();
            Date createdDate = new Date();
            post.setCreatedDate(createdDate);
            post.setLastModificated(createdDate);
            post.setAuthor(user);
            if (!file.isEmpty()) {
                String folder = context.getRealPath("/image/");
                Path path = Paths.get(folder + post.getTitle() + "-" + file.getOriginalFilename());
                try {
                    file.transferTo(path);
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                post.setImage(post.getTitle() + "-" + file.getOriginalFilename());
            } else {
                post.setImage("");
            }
        } else {
            Post orginalPost = getPost(post.getId());
            post.setAuthor(orginalPost.getAuthor());
            post.setCreatedDate(orginalPost.getCreatedDate());
            Date lastModificated = new Date();
            post.setLastModificated(lastModificated);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = session.createQuery("FROM User WHERE username='" + auth.getName() + "'", User.class).getSingleResult();
            post.setLastModificatedBy(user);
            if (!file.isEmpty()) {
                String folder = context.getRealPath("/image/");
                Path path = Paths.get(folder + post.getTitle() + "-" + file.getOriginalFilename());
                try {
                    file.transferTo(path);
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                post.setImage(post.getTitle() + "-" + file.getOriginalFilename());
                if (!orginalPost.getImage().equals("")) {
                    String folder2 = context.getRealPath("/image/");
                    File file2 = new File(folder2 + orginalPost.getImage());
                    file2.delete();
                }
            } else {
                post.setImage(orginalPost.getImage());
            }
        }
        Post managedEntity = (Post) session.merge(post);
        return managedEntity.getId();
    }

    @Override
    public Post getPost(int postId) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Post.class, postId);
    }

    @Override
    public List<Post> getPostsByPage(int page, int postsOnOnePage) {
        Session session = entityManager.unwrap(Session.class);
        int minRowNum;
        if (page == 1) {
            minRowNum = 0;
        } else {
            minRowNum = (page - 1) * postsOnOnePage;
        }
        return session.createQuery("FROM Post", Post.class)
                .setFirstResult(minRowNum).setMaxResults(postsOnOnePage)
                .getResultList();
    }

    @Override
    public int getNumberOfAllPosts() {
        Session session = entityManager.unwrap(Session.class);
        List<Post> posts = session.createQuery("FROM Post", Post.class).getResultList();
        return posts.size();
    }

    @Override
    public List<Post> getPostsByPageAndSearch(int page, int postsOnOnePage, String q) {
        Session session = entityManager.unwrap(Session.class);
        List<Post> posts = new ArrayList<>();
        int minRowNum;
        if (page == 1) {
            minRowNum = 0;
        } else {
            minRowNum = (page - 1) * postsOnOnePage;
        }
        String[] split_q = q.split(" ");
        for (String sq : split_q) {
            List<Post> posts_temp = session.createQuery("FROM Post p WHERE lower(p.title) like lower(concat('%','" + sq + "','%')) " +
                    "or lower(p.description) like lower(concat('%','" + sq + "','%')) or p.id in (SELECT pt.post FROM PostTag pt WHERE pt.tag='" + sq + "')", Post.class)
                    .setFirstResult(minRowNum).setMaxResults(postsOnOnePage)
                    .getResultList();
            for (Post post : posts_temp) {
                if (!posts.contains(post)) {
                    posts.add(post);
                }
            }
        }
        return posts;
    }

    @Override
    public int getNumberOfAllSearchedPosts(String q) {
        Session session = entityManager.unwrap(Session.class);
        List<Post> posts = session.createQuery("FROM Post p WHERE lower(p.title) like lower(concat('%','" + q + "','%')) " +
                "or lower(p.description) like lower(concat('%','" + q + "','%'))", Post.class).getResultList();
        return posts.size();
    }
}
