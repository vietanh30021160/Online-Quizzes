package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface IQuizAttemptsService {
    @Transactional
    void deleteQuizAttemptsByQuizId(Integer quizId);

    List<QuizAttempt> getQuizAttemptsByQuizId(Integer quizId);

    List<QuizAttempt> getAll();
    Boolean create(QuizAttempt quizAttempts);
    QuizAttempt findById(Integer AttemptsID);
    Boolean update(QuizAttempt quizAttempts);
    Boolean delete(Integer AttemptsID);
    List<QuizAttempt> findQuizAttemptsByQuizID(Integer QuizzID,Sort sort);
    Page<QuizAttempt> findQuizAttemptsByQuizID(Integer QuizzID, Integer pageNo, Sort sort);

    List<QuizAttempt> searchUseByName(String username, Integer QuizzID);
    Page<QuizAttempt> searchUseByName(String username, Integer QuizzID, Integer pageNo);

    Page<QuizAttempt> getAll(Integer pageNo);

    public QuizAttempt getQuizAttempts(Integer quizAttemptID);

    public boolean createQuizzAttempt(QuizAttempt attempt);

    public QuizAttempt updateAttempts(Integer id, QuizAttempt attempt);

    public List<QuizAttempt> getAttemptByUserIdAndQuizzId(Quiz quiz, User user);

    public List<QuizAttempt> findByQuizIdAndUserIdAndStartTime(Quiz quiz, User user, Timestamp startTime);
}
