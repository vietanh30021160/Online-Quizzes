package com.swp.online_quizz.Service;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.QuizAttempts;

@Service
public interface IQuizAttemptsService {
    public QuizAttempts getQuizAttempts(Integer quizAttemptID);

    public boolean createQuizzAttempt(QuizAttempts attempt);
}
