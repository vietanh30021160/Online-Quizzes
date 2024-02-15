package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuizProgress;
import org.springframework.transaction.annotation.Transactional;

public interface IQuizProgressService {

    @Transactional
    void deleteQuizProcessByQuizId(Integer quizId);

    public Boolean createQuizProgress(QuizProgress quizProgress);

    public Boolean updateQuizProgress(Integer id, QuizProgress quizProgress);
}
