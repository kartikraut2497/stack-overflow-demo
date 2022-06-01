package com.intuit.stackoverflowdemo.repository;

import com.intuit.stackoverflowdemo.models.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    public List<Question> findByCreatorMemberId(long memberId);

    @Query("select q from Question q where q.questionId in :questionIds order by FIELD(q.questionId, :questionIds)")
    public List<Question> getAllQuestionsByIds(List<Long> questionIds);
}
