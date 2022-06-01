package com.intuit.stackoverflowdemo.repository;

import com.intuit.stackoverflowdemo.models.Answer;
import com.intuit.stackoverflowdemo.models.Comment;
import com.intuit.stackoverflowdemo.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    public List<Comment> findByCreatorMemberId(Long memberId);

    public List<Comment> findByQuestionQuestionId(Long questionId);

    public List<Comment> findByAnswerAnswerId(Long answerId);
}
