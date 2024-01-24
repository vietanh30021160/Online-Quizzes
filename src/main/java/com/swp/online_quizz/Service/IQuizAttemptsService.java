package com.swp.online_quizz.Service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.QuizAttempts;
import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Entity.Users;

@Service
public interface IQuizAttemptsService {
    public QuizAttempts getQuizAttempts(Integer quizAttemptID);

    public boolean createQuizzAttempt(QuizAttempts attempt);

    public List<QuizAttempts> getAttemptByUserIdAndQuizzId(Quizzes quiz, Users user);

    public List<QuizAttempts> findByQuizIdAndUserIdAndStartTime(Quizzes quiz, Users user, Timestamp startTime);
}
