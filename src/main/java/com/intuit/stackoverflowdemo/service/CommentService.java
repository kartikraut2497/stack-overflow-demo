package com.intuit.stackoverflowdemo.service;

import com.intuit.stackoverflowdemo.models.Comment;
import com.intuit.stackoverflowdemo.models.Question;
import com.intuit.stackoverflowdemo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllCommentsByUser(Long userId){
        List<Comment> commentList = commentRepository.findByCreatorMemberId(userId);
        return commentList;
    }

    public List<Comment> getAllCommentsByQuestion(Long qId) {
        List<Comment> commentList = commentRepository.findByQuestionQuestionId(qId);
        return commentList;
    }

    public List<Comment> getAllCommentsByAnswer(Long aId) {
        List<Comment> commentList = commentRepository.findByAnswerAnswerId(aId);
        return commentList;
    }

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }
}
