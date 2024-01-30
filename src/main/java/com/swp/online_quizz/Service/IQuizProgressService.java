package com.swp.online_quizz.Service;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.QuizProgress;

public interface IQuizProgressService {

    public Boolean createQuizProgress(QuizProgress quizProgress);

    public Boolean updateQuizProgress(Integer id, QuizProgress quizProgress);
}
