package com.intuit.stackoverflowdemo.models;

import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Entity(name = "Answer")
public class Answer extends StackOverflowItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    private String answerText;
    private String photos;
    private Boolean isOfficialAnswer = false;

    @ManyToOne
    private Question question;

    public Long getAnswerId() {
        return answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Boolean getOfficialAnswer() {
        return isOfficialAnswer;
    }

    public void setOfficialAnswer(Boolean officialAnswer) {
        isOfficialAnswer = officialAnswer;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }
}
