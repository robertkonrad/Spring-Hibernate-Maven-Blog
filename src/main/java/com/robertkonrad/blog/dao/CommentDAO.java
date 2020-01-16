package com.robertkonrad.blog.dao;

import java.util.List;

import com.robertkonrad.blog.entity.Comment;

public interface CommentDAO {

	List<Comment> getComments(int postId);

	void saveComment(int postId, Comment comment);

}
