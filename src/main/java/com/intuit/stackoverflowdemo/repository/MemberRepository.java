package com.intuit.stackoverflowdemo.repository;

import com.intuit.stackoverflowdemo.models.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
