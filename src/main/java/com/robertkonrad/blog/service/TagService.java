package com.robertkonrad.blog.service;

import com.robertkonrad.blog.entity.Tag;

import java.util.List;

public interface TagService {
    public List<Tag> getTags();

    public List<Tag> getPostTags(int postId);

    public void savePostTags(int postId, List<String> tags);

    public Tag getTag(String tagName);

    public void deleteTag(String tagName);

    public void saveTag(Tag tag);
}
