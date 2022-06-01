package com.intuit.stackoverflowdemo.service;

import com.intuit.stackoverflowdemo.enums.VoteType;
import com.intuit.stackoverflowdemo.models.Answer;
import com.intuit.stackoverflowdemo.models.Comment;
import com.intuit.stackoverflowdemo.models.Member;
import com.intuit.stackoverflowdemo.models.Question;
import com.intuit.stackoverflowdemo.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.intuit.stackoverflowdemo.Constants.VIEWS;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CommentService commentService;

    public List<Answer> getAllAnswersByUser(Long userId){
        List<Answer> answerList = new ArrayList<>();
        answerList = answerRepository.findByCreatorMemberId(userId);

        return answerList;
    }

    public List<Answer> getAllAnswerByQuestion(Long qId) {
        List<Answer> answerList = new ArrayList<>();
//        answerList.stream().filter(a -> a.getOfficialAnswer().equals(Boolean.TRUE));
        return answerList;
    }

    public Answer addAnswer(Answer answer){
        return answerRepository.save(answer);
    }

    public Boolean addComment(Long memberId, Long aId, Comment comment) {
        Member currentMember = getMember(memberId);
        Answer answer = answerRepository.findById(aId).orElse(null);

        if(currentMember != null && answer != null){
            comment.setAnswer(answer);
            comment.setCreator(currentMember);
            commentService.addComment(comment);
            return true;
        }

        return false;
    }

    private Member getMember(Long memberId){
        return memberService.getMember(memberId);
    }

    public void voteAnswer(Long aId, Long memberId, String voteType) {
        Answer answer = answerRepository.findById(aId).orElse(null);
        Member member = getMember(memberId);
        VoteType voteTypeEnum = getVoteTypeEnum(voteType);

        if(answer != null && member != null && voteTypeEnum != null){
            int currVotes = getUpvotes(answer.getUpVotes(), voteTypeEnum);
            answer.setUpVotes(currVotes);
            answerRepository.save(answer);
        }
    }

    private int getUpvotes(int currUpvotes, VoteType voteTypeEnum) {
        if(VoteType.UPVOTE.equals(voteTypeEnum)){
            currUpvotes++;
        }
        else if(VoteType.DOWNVOTE.equals(voteTypeEnum)){
            currUpvotes--;
        }
        return currUpvotes;
    }

    private VoteType getVoteTypeEnum(String voteType) {
        for(VoteType voteTypeEnum: VoteType.values()){
            if(voteTypeEnum.name().equalsIgnoreCase(voteType)){
                return voteTypeEnum;
            }
        }

        return null;
    }

    public Boolean acceptAnswer(Long memberId, Long aId) {
        Answer answer = answerRepository.findById(aId).orElse(null);
        Question question = answer.getQuestion();

        if(question != null && answer != null){
            Member questionCreator = question.getCreator();
            if(memberId == questionCreator.getMemberId()){
                answer.setOfficialAnswer(true);
                answerRepository.save(answer);
                return true;
            }
        }

        return false;
    }

    public Boolean editAnswer(Long memberId, Long aId, Answer newAnswer) {
        Answer currAnswer = answerRepository.findById(aId).orElse(null);

        if(currAnswer != null){
            Member answerCreator = currAnswer.getCreator();
            if(memberId == answerCreator.getMemberId()){
                currAnswer.setAnswerText(newAnswer.getAnswerText());
                answerRepository.save(currAnswer);

                return true;
            }
        }

        return false;
    }

}
