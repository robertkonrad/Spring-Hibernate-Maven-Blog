package com.robertkonrad.blog.service;

import com.robertkonrad.blog.dao.TagDAO;
import com.robertkonrad.blog.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDAO tagDAO;

    @Transactional
    @Override
    public List<Tag> getTags() {
        return tagDAO.getTags();
    }

    @Transactional
    @Override
    public List<Tag> getPostTags(int postId) {
        return tagDAO.getPostTags(postId);
    }

    @Transactional
    @Override
    public void savePostTags(int postId, List<String> tags) {
        tagDAO.savePostTags(postId, tags);
    }

    @Transactional
    @Override
    public Tag getTag(String tagName) {
        return tagDAO.getTag(tagName);
    }

    @Transactional
    @Override
    public void deleteTag(String tagName) {
        tagDAO.deleteTag(tagName);
    }

    @Transactional
    @Override
    public void saveTag(Tag tag) {
        tagDAO.saveTag(tag);
    }
}
