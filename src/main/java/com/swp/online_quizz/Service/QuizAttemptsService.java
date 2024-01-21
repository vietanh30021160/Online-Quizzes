package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuizAttempts;

import java.util.List;

public interface QuizAttemptsService {
    List<QuizAttempts> getAll();
    Boolean create(QuizAttempts quizAttempts);
    QuizAttempts findById(Integer AttemptsID);
    Boolean update(QuizAttempts quizAttempts);
    Boolean delete(Integer AttemptsID);
}
