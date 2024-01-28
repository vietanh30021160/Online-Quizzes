package com.swp.online_quizz.Service;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.QuestionAttempt;

@Service
public interface IQuesstionAttemptsService {
    public Boolean createQuesstionAttempts(QuestionAttempt questionAttempts);

    public Boolean updateQuesstionAttempts(Integer id, QuestionAttempt questionAttempts);
}
