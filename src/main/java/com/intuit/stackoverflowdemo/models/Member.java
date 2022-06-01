package com.intuit.stackoverflowdemo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    String name;
    String userName;
    String emailId;

    public Long getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return memberId.equals(member.memberId) && Objects.equals(name, member.name) && Objects.equals(userName, member.userName) && Objects.equals(emailId, member.emailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, name, userName, emailId);
    }
}
