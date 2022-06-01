package com.intuit.stackoverflowdemo.repository;

import com.intuit.stackoverflowdemo.models.Answer;
import com.intuit.stackoverflowdemo.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    public List<Answer> findByCreatorMemberId(Long memberId);

    public List<Answer> findByQuestionQuestionId(Long questionId);
}
