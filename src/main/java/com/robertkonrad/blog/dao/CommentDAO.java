package com.robertkonrad.blog.dao;

import com.robertkonrad.blog.entity.Comment;

import java.util.List;

public interface CommentDAO {

    List<Comment> getComments(int postId);

    void saveComment(int postId, Comment comment);

}
