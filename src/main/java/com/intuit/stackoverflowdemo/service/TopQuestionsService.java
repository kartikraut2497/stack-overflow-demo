package com.intuit.stackoverflowdemo.service;

import com.intuit.stackoverflowdemo.models.Question;

import java.util.List;

public interface TopQuestionsService {

    public List<Question> getTopQuestions();

}
