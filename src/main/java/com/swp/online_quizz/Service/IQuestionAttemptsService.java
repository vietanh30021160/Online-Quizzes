package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuestionAttempt;

import java.util.List;


public interface IQuestionAttemptsService {
    List<QuestionAttempt> findByAttemptID(Integer attemptID);
}
