package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuestionAttempts;
import org.springframework.stereotype.Service;

@Service
public interface IQuesstionAttemptsService {
    public Boolean createQuesstionAttempts(QuestionAttempts questionAttempts);
}
