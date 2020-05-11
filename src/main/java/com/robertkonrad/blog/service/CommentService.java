package com.robertkonrad.blog.service;

import com.robertkonrad.blog.entity.Comment;

import java.util.List;

public interface CommentService {

    public List<Comment> getComments(int postId);

    public void saveComment(int postId, Comment comment);

    public void deleteComment(int commentId);
}
