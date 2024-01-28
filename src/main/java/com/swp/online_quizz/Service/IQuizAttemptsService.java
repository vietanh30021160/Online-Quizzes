package com.swp.online_quizz.Service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.User;

@Service
public interface IQuizAttemptsService {
    public QuizAttempt getQuizAttempts(Integer quizAttemptID);

    public boolean createQuizzAttempt(QuizAttempt attempt);

    public QuizAttempt updateAttempts(Integer id, QuizAttempt attempt);

    public List<QuizAttempt> getAttemptByUserIdAndQuizzId(Quiz quiz, User user);

    public List<QuizAttempt> findByQuizIdAndUserIdAndStartTime(Quiz quiz, User user, Timestamp startTime);
}
