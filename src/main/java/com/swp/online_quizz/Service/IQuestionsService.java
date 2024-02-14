package com.swp.online_quizz.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.Question;
import org.springframework.transaction.annotation.Transactional;

public interface IQuestionsService {
    public List<Question> getAllQuestions();

    public Question getQuestions(Integer questionId);

    boolean createQuestion1(Question question);

    Question getQuestionById(Integer questionId);

    Question findQuestionById(Integer questionId);

    Boolean updateQuestion1(Integer id, Question question);

    List<Question> getQuestionsByQuizId(Integer quizId);

    @Transactional
    void deleteQuestionsByQuizId(Integer quizId);
}
