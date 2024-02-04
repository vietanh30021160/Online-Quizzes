package com.swp.online_quizz.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.Question;

public interface IQuestionsService {
    public List<Question> getAllQuestions();

    public Question getQuestions(Integer questionId);

    boolean createQuestion1(Question question);

    Question getQuestionById(Integer questionId);

    Question findQuestionById(Integer questionId);
}
