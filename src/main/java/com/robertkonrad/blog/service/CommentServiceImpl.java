package com.robertkonrad.blog.service;

import com.robertkonrad.blog.dao.CommentDAO;
import com.robertkonrad.blog.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDAO commentDAO;

    @Transactional
    @Override
    public List<Comment> getComments(int postId) {
        List<Comment> comments = commentDAO.getComments(postId);
        return comments;
    }


    @Transactional
    @Override
    public void saveComment(int postId, Comment comment) {
        commentDAO.saveComment(postId, comment);
    }

}
