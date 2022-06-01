package com.intuit.stackoverflowdemo.service;

import com.intuit.stackoverflowdemo.models.Question;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.intuit.stackoverflowdemo.Constants.HASH_KEY;
import static com.intuit.stackoverflowdemo.Constants.NUM_TOP_QUESTIONS;


@Service
public class RedisService {

    private RedisTemplate<Long, Long> redisTemplate;
    private HashOperations hashOperations;

    public RedisService(RedisTemplate<Long, Long> redisTemplate){
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public void addViews(Long qId){
        Long currentViews = hashOperations.get(HASH_KEY, qId) != null ? (Long) hashOperations.get(HASH_KEY, qId) : 0;
        hashOperations.put(HASH_KEY, qId, ++currentViews);
    }

    public Map<Long, Long> getAllQuestionViews(){
        Map<Long, Long> allEntriesMap = hashOperations.entries(HASH_KEY);
        return allEntriesMap;
    }

    public List<Long> getTopQuestionsByViews(){
        Map<Long, Long> allEntriesMap = hashOperations.entries(HASH_KEY);
        List<Long> topQuestionIdList = allEntriesMap.entrySet().stream()
                .sorted((e1, e2) -> (int) (e2.getValue() - e1.getValue()))
                .limit(NUM_TOP_QUESTIONS)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return topQuestionIdList;
    }
}
