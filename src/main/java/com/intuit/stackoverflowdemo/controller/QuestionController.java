package com.intuit.stackoverflowdemo.controller;

import com.intuit.stackoverflowdemo.models.Answer;
import com.intuit.stackoverflowdemo.models.Comment;
import com.intuit.stackoverflowdemo.models.Question;
import com.intuit.stackoverflowdemo.service.AnswerService;
import com.intuit.stackoverflowdemo.service.CommentService;
import com.intuit.stackoverflowdemo.service.QuestionService;
import com.intuit.stackoverflowdemo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/")
    public List<Question> getAllQuestions(){
        List<Question> questionList = questionService.getAllQuestions();
        return questionList;
    }

    @GetMapping("/{qId}")
    public Question getQuestion(@PathVariable Long qId){
        Question question = questionService.getQuestionById(qId);
        return question;
    }

    @GetMapping("/{qId}/answers")
    public List<Answer> getAllAnswersOfQuestion(@PathVariable Long qId){
        List<Answer> answerList = answerService.getAllAnswerByQuestion(qId);
        return answerList;
    }

    @GetMapping("/{qId}/comments")
    public List<Comment> getAllCommentsOfQuestion(@PathVariable Long qId){
        List<Comment> commentList = commentService.getAllCommentsByQuestion(qId);
        return commentList;
    }

    @PostMapping("/")
    public Boolean addQuestion(@RequestHeader("memberId") Long memberId, @RequestBody Question question){
        return questionService.addQuestion(question, memberId);
    }

    @PutMapping("/{qId}/edit")
    public Boolean editQuestion(@RequestHeader("memberId") Long memberId, @PathVariable Long qId, @RequestBody Question question){
        return questionService.editQuestion(qId, question, memberId);
    }

    @PostMapping("/{qId}/{voteType}")
    public void voteQuestion(@RequestHeader("memberId") Long memberId, @PathVariable Long qId, @PathVariable String voteType){
        questionService.voteQuestion(qId, memberId, voteType);
    }

    @PostMapping("/{qId}/answers")
    public Boolean addAnswer(@RequestHeader("memberId") Long memberId, @PathVariable Long qId, @RequestBody Answer answer){
        return questionService.addAnswer(memberId, qId, answer);
    }

    @PostMapping("/{qId}/answerWithFile")
    public void addAnswerWithFile(@RequestHeader("memberId") Long memberId, @PathVariable Long qId, @RequestPart("image") MultipartFile multipartFile, @RequestPart("answer") Answer answer){
        questionService.addAnswerWithPhoto(qId, memberId, answer, multipartFile);
    }

    @PostMapping("/{qId}/comments")
    public Boolean addComment(@RequestHeader("memberId") Long memberId, @PathVariable Long qId, @RequestBody Comment comment){
        return questionService.addComment(memberId, qId, comment);
    }

    @GetMapping("/getTopQuestions")
    public List<Question> getTopQuestions(@RequestParam("topBy") String topQuestionsParam){
        return questionService.getTopQuestions(topQuestionsParam);
    }

    @GetMapping("/getAllViews")
    public Map<Long, Long> getAllQuestionViews(){
        return redisService.getAllQuestionViews();
    }

}
