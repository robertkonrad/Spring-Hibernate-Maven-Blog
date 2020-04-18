package com.robertkonrad.blog.service;

import com.robertkonrad.blog.entity.Comment;

import java.util.List;

;

public interface CommentService {

    List<Comment> getComments(int postId);

    void saveComment(int postId, Comment comment);

}
