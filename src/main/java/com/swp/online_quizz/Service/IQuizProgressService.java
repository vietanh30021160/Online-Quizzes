package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuizProgress;
import org.springframework.stereotype.Service;

@Service
public interface IQuizProgressService {

    public Boolean createQuizProgress(QuizProgress quizProgress);
}

