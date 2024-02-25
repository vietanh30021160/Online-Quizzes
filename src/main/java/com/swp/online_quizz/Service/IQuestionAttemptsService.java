package com.swp.online_quizz.Service;

import java.util.List;

import com.swp.online_quizz.Entity.QuestionAttempt;
import org.springframework.transaction.annotation.Transactional;

public interface IQuestionAttemptsService {


    @Transactional
    void deleteQuestionAttemptsByQuizId(Integer quizId);

    public Boolean createQuesstionAttempts(QuestionAttempt questionAttempts);

    public Boolean updateQuesstionAttempts(Integer id, QuestionAttempt questionAttempts);

    List<QuestionAttempt> findByAttemptID(Integer attemptID);
}
