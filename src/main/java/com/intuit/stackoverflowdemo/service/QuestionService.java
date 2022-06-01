package com.intuit.stackoverflowdemo.service;

import com.intuit.stackoverflowdemo.enums.VoteType;
import com.intuit.stackoverflowdemo.models.Answer;
import com.intuit.stackoverflowdemo.models.Comment;
import com.intuit.stackoverflowdemo.models.Member;
import com.intuit.stackoverflowdemo.models.Question;
import com.intuit.stackoverflowdemo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.intuit.stackoverflowdemo.Constants.VIEWS;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisService redisService;

    public List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<>();
        questionRepository.findAll().forEach(questionList::add);
        return questionList;
    }

    public List<Question> getAllQuestionsByUser(Long userId){
        List<Question> questionList = new ArrayList<>();
        questionList = questionRepository.findByCreatorMemberId(userId);
        return questionList;
    }

    public Question getQuestionById(Long qId){
        Question question = questionRepository.findById(qId).orElse(null);
        if(question != null){
            redisService.addViews(qId);
        }
        return questionRepository.findById(qId).get();
    }

    public Boolean addQuestion(Question question, Long memberId){
        Member member = memberService.getMember(memberId);

        if(null != member){
            question.setCreator(member);
            questionRepository.save(question);
            return true;
        }
        return false;
    }

    public Boolean editQuestion(Long qId, Question newQuestion, Long memberId){
        Member editingMember = memberService.getMember(memberId);
        Question currentQuestion = questionRepository.findById(qId).orElseThrow(() -> new RuntimeException("Question Not Found"));
        Member creatorMember = currentQuestion.getCreator();

        if(editingMember != null && creatorMember.equals(editingMember)){
            currentQuestion.setTitle(newQuestion.getTitle());
            currentQuestion.setDesc(newQuestion.getDesc());

            questionRepository.save(currentQuestion);
            return true;
        }

        return false;
    }

    // Use Optional
    public Boolean addAnswer(Long memberId, Long qId, Answer answer) {
        Member currentMember = getMember(memberId);
        Question question = questionRepository.findById(qId).orElse(null);

        if(currentMember != null && question != null){
            answer.setQuestion(question);
            answer.setCreator(currentMember);
            answerService.addAnswer(answer);
            return true;
        }

        return false;
    }

    // Use Optional
    public Boolean addComment(Long memberId, Long qId, Comment comment) {
        Member currentMember = getMember(memberId);
        Question question = questionRepository.findById(qId).orElse(null);

        if(currentMember != null && question != null){
            comment.setQuestion(question);
            comment.setCreator(currentMember);
            commentService.addComment(comment);
            return true;
        }

        return false;
    }

    // Use Optional
    public void voteQuestion(Long qId, Long memberId, String voteType) {
        Question question = questionRepository.findById(qId).orElse(null);
        Member member = getMember(memberId);
        VoteType voteTypeEnum = getVoteTypeEnum(voteType);

        if(member != null && question != null && voteTypeEnum != null){
            int currUpvotes = getUpvotes(question.getUpVotes(), voteTypeEnum);

            question.setUpVotes(currUpvotes);
            questionRepository.save(question);
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

    private Member getMember(Long memberId){
        return memberService.getMember(memberId);
    }


    public void addAnswerWithPhoto(Long qId, Long memberId, Answer answer, MultipartFile multipartFile) {
        Member creatorMember = getMember(memberId);
        Question currentQuestion = questionRepository.findById(qId).orElse(null);

        try {
            if(currentQuestion != null & answer != null && creatorMember != null) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                answer.setPhotos(fileName);
                answer.setCreator(creatorMember);
                answer.setQuestion(currentQuestion);

                Answer savedAnswer = answerService.addAnswer(answer);
                String uploadDir = "answer-Photos/" + savedAnswer.getAnswerId();

                FileUploadService.saveFile(uploadDir, fileName, multipartFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Question> getTopQuestions(String topQuestionsParam) {
        TopQuestionsService topQuestionsService = topQuestionsFactory(topQuestionsParam);

        if(topQuestionsService != null){
            return topQuestionsService.getTopQuestions();
        }

        return null;
    }

    private TopQuestionsService topQuestionsFactory(String topQuestionsParam){
        if(VIEWS.equalsIgnoreCase(topQuestionsParam)){
            TopQuestionsService topQuestionsService = () -> {
                List<Long> topQuestionIds = redisService.getTopQuestionsByViews();
                for(Long id: topQuestionIds){
                    System.out.println(id);
                }
                List<Question> topQuestionsList = questionRepository.getAllQuestionsByIds(topQuestionIds);
                return topQuestionsList;
            };
            return topQuestionsService;
        }

        return null;
    }
}
