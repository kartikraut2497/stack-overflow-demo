package com.intuit.stackoverflowdemo.service;

import com.intuit.stackoverflowdemo.models.Member;
import com.intuit.stackoverflowdemo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public void addMember(Member member){
        memberRepository.save(member);
    }

    public Member getMember(Long memberId){
        return memberRepository.findById(memberId).orElse(null);
    }

}
