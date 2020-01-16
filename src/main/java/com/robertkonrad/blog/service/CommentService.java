package com.robertkonrad.blog.service;

import java.util.List;

import com.robertkonrad.blog.entity.Comment;;

public interface CommentService {

	List<Comment> getComments(int postId);

	void saveComment(int postId, Comment comment);

}
