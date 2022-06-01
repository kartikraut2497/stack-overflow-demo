package com.intuit.stackoverflowdemo.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
public class StackOverflowItem {

    @CreationTimestamp
    Date createdDate;
    int upVotes;

    @OneToOne
    private Member creator;

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public Member getCreator() {
        return creator;
    }

    public void setCreator(Member creator) {
        this.creator = creator;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate == null ? new Date() : createdDate;
    }

}
