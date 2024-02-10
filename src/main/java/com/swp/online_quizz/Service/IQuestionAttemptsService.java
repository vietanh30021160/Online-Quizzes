package com.swp.online_quizz.Service;

import java.util.List;

import com.swp.online_quizz.Entity.QuestionAttempt;

public interface IQuestionAttemptsService {
    public Boolean createQuesstionAttempts(QuestionAttempt questionAttempts);

    public Boolean updateQuesstionAttempts(Integer id, QuestionAttempt questionAttempts);

    List<QuestionAttempt> findByAttemptID(Integer attemptID);
}
