package com.swp.online_quizz.Service;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.QuestionAttempt;

import java.util.List;

public interface IQuestionAttemptsService {
    public Boolean createQuesstionAttempts(QuestionAttempt questionAttempts);

    public Boolean updateQuesstionAttempts(Integer id, QuestionAttempt questionAttempts);
    List<QuestionAttempt> findByAttemptID(Integer attemptID);
}
