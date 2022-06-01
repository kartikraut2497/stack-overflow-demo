package com.intuit.stackoverflowdemo.controller;

import com.intuit.stackoverflowdemo.models.Answer;
import com.intuit.stackoverflowdemo.models.Comment;
import com.intuit.stackoverflowdemo.models.Member;
import com.intuit.stackoverflowdemo.service.AnswerService;
import com.intuit.stackoverflowdemo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/{aId}/comments")
    public List<Comment> getAllComments(@PathVariable Long aId){
        return commentService.getAllCommentsByAnswer(aId);
    }

    @PostMapping("/{aId}/comments")
    public Boolean addComment(@RequestHeader("memberId") Long memberId, @PathVariable Long aId, @RequestBody Comment comment){
        return answerService.addComment(memberId, aId, comment);
    }

    @PostMapping("/{aId}/{voteType}")
    public void voteQuestion(@RequestHeader("memberId") Long memberId, @PathVariable Long aId, @PathVariable String voteType){
        answerService.voteAnswer(aId, memberId, voteType);
    }

    @PostMapping("/{aId}/accept")
    public Boolean acceptAnswer(@RequestHeader("memberId") Long memberId, @PathVariable Long aId){
        return answerService.acceptAnswer(memberId, aId);
    }

    @PutMapping("/{aId}/edit")
    public Boolean editAnswer(@RequestHeader("memberId") Long memberId, @PathVariable Long aId, @RequestBody Answer answer){
        return answerService.editAnswer(memberId, aId, answer);
    }

//    @PostMapping("/{aId}/addPhoto")
//    public void addAnswerWithPhoto(@PathVariable Long aId, @RequestPart("image") MultipartFile multipartFile, @RequestPart("answer") Answer answer){
//        answerService.addAnswerWithPhoto(aId, multipartFile);
//    }

}
