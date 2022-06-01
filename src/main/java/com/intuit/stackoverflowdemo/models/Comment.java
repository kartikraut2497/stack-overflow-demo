package com.intuit.stackoverflowdemo.models;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;

@Entity(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate;

    @OneToOne
    private Member creator;
    private String commentText;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Answer answer;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Member getCreator() {
        return creator;
    }

    public void setCreator(Member creator) {
        this.creator = creator;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
