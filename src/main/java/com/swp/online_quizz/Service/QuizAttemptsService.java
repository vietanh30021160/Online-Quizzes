package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuizAttempts;
import org.springframework.data.domain.Page;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface QuizAttemptsService {
    List<QuizAttempts> getAll();
    Boolean create(QuizAttempts quizAttempts);
    QuizAttempts findById(Integer AttemptsID);
    Boolean update(QuizAttempts quizAttempts);
    Boolean delete(Integer AttemptsID);
    List<QuizAttempts> findQuizAttemptsByQuizID(Integer QuizzID);
    List<QuizAttempts> searchUseByName(String useName);

    Page<QuizAttempts> getAll(Integer pageNo);

    Page<QuizAttempts> searchUseByName(String useName, Integer pageNo);
}
