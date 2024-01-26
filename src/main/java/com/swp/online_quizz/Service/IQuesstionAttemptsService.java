package com.swp.online_quizz.Service;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.QuestionAttempts;

@Service
public interface IQuesstionAttemptsService {
    public Boolean createQuesstionAttempts(QuestionAttempts questionAttempts);

    public Boolean updateQuesstionAttempts(Integer id, QuestionAttempts questionAttempts);
}
