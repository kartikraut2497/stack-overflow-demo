package com.intuit.stackoverflowdemo.controller;

import com.intuit.stackoverflowdemo.models.Answer;
import com.intuit.stackoverflowdemo.models.Comment;
import com.intuit.stackoverflowdemo.models.Member;
import com.intuit.stackoverflowdemo.models.Question;
import com.intuit.stackoverflowdemo.service.AnswerService;
import com.intuit.stackoverflowdemo.service.CommentService;
import com.intuit.stackoverflowdemo.service.MemberService;
import com.intuit.stackoverflowdemo.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CommentService commentService;

    private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);

    @PostMapping("/")
    public void addMember(@RequestBody Member member){
        if(member != null){
            memberService.addMember(member);
        }
    }

    @GetMapping("{id}/questions")
    public List<Question> getAllQuestionsByUser(@PathVariable Long id){
        List<Question> questionList = questionService.getAllQuestionsByUser(id);
        return questionList;
    }

    @GetMapping("{id}/answers")
    public List<Answer> getAllAnswersByUser(@PathVariable Long id){
        List<Answer> answerList = answerService.getAllAnswersByUser(id);
        return answerList;
    }

    @GetMapping("{id}/comments")
    public List<Comment> getAllCommentsByUser(@PathVariable Long id){
        List<Comment> commentList = commentService.getAllCommentsByUser(id);
        return commentList;
    }
}
