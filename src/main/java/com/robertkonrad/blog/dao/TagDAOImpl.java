package com.robertkonrad.blog.dao;

import com.robertkonrad.blog.entity.Post;
import com.robertkonrad.blog.entity.PostTag;
import com.robertkonrad.blog.entity.Tag;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TagDAOImpl implements TagDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Tag> getTags() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("FROM Tag", Tag.class).getResultList();
    }

    @Override
    public List<Tag> getPostTags(int postId) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("From Tag t WHERE t.tag in (SELECT pt.tag FROM PostTag pt WHERE pt.post = '" + postId + "')", Tag.class).getResultList();
    }

    @Override
    public void savePostTags(int postId, List<String> tags) {
        Session session = entityManager.unwrap(Session.class);
        Post post = session.get(Post.class, postId);
        List<Tag> currentTags = getPostTags(postId);
        if (currentTags.isEmpty()) {
            for (String tag : tags) {
                Tag tag_temp = session.get(Tag.class, tag);
                PostTag postTag = new PostTag();
                postTag.setPost(post);
                postTag.setTag(tag_temp);
                session.save(postTag);
            }
        } else {
            for (String tag : tags) {
                List<PostTag> postTagQ = session.createQuery("FROM PostTag pt WHERE pt.tag = '" + tag + "' and pt.post = '" + postId + "'", PostTag.class).getResultList();
                if (postTagQ.isEmpty()) {
                    Tag tag_temp = session.get(Tag.class, tag);
                    PostTag postTag = new PostTag();
                    postTag.setPost(post);
                    postTag.setTag(tag_temp);
                    session.save(postTag);
                }
            }
            for (Tag currentTag : currentTags) {
                if (!tags.contains(currentTag.getTag())) {
                    PostTag postTag = session.createQuery("FROM PostTag pt WHERE pt.tag = '" + currentTag.getTag() + "' and pt.post = '" + postId + "'", PostTag.class).getSingleResult();
                    session.delete(postTag);
                }
            }
        }
    }

    @Override
    public Tag getTag(String tagName) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Tag.class, tagName);
    }

    @Override
    public void deleteTag(String tagName) {
        Session session = entityManager.unwrap(Session.class);
        Tag tag = session.get(Tag.class, tagName);
        List<PostTag> postTags = session.createQuery("FROM PostTag pt WHERE pt.tag = '" + tagName + "'", PostTag.class).getResultList();
        for (PostTag postTag : postTags) {
            session.delete(postTag);
        }
        session.delete(tag);
    }

    @Override
    public void saveTag(Tag tag) {
        Session session = entityManager.unwrap(Session.class);
        session.save(tag);
    }

}
