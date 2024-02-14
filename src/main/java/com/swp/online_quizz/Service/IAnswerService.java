package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IAnswerService {
    public Answer getAnswers(Integer answerId);

    List<Answer> getAll();

    boolean createAnswer1(Answer answer, Integer questionId);
    Boolean updateAnswer1(Integer id, Answer answer);

    @Transactional
    void deleteAnswersByQuestionId(Integer questionId);

    Answer getAnswerById(Integer questionId);
}
