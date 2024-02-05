package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answer;
import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Repository.AnswersRepository;
import com.swp.online_quizz.Repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService implements IAnswerService{
    @Autowired
    AnswersRepository answersRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private QuestionsService questionsService;
    @Override
    public Answer getAnswers(Integer answerId) {
        return answersRepository.getReferenceById(answerId);
    }
    @Override
    public List<Answer> getAll() {
        return answersRepository.findAll();
    }
    @Override
    public boolean createAnswer1(Answer answer, Integer questionId) {
        try {
            Question existingQuestion = questionsService.getQuestionById(questionId);
            //answer.setIsCorrect(true);
            answer.setQuestion(existingQuestion);
            answersRepository.save(answer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public Boolean updateAnswer1(Integer id, Answer answer) {
        try {
            Answer uAnswer = answersRepository.getReferenceById(id);
            uAnswer.setAnswerContent(answer.getAnswerContent());
            uAnswer.setIsCorrect(answer.getIsCorrect());
            this.answersRepository.save(uAnswer);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    }

