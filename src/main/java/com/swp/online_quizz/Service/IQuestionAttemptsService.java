package com.swp.online_quizz.Service;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.QuestionAttempts;

@Service
public interface IQuestionAttemptsService {
    public boolean createQuestionAttempt(QuestionAttempts questionAttempts);
}
