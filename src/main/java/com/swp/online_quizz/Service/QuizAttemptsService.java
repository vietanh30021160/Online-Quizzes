package com.swp.online_quizz.Service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.QuizAttempts;
import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Entity.Users;
import com.swp.online_quizz.Repository.QuizAttemptsRepository;

@Service
public class QuizAttemptsService implements IQuizAttemptsService {
    @Autowired
    QuizAttemptsRepository quizAttemptsRepository;

    @Override
    public QuizAttempts getQuizAttempts(Integer quizAttemptID) {
        return quizAttemptsRepository.getReferenceById(quizAttemptID);
    }

    @Override
    public boolean createQuizzAttempt(QuizAttempts attempt) {
        try {
            this.quizAttemptsRepository.save(attempt);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<QuizAttempts> getAttemptByUserIdAndQuizzId(Quizzes quiz, Users user) {
        return quizAttemptsRepository.findByQuizIdAndUserId(quiz.getQuizId(), user.getUserId());
    }

    @Override
    public List<QuizAttempts> findByQuizIdAndUserIdAndStartTime(Quizzes quiz, Users user, Timestamp startTime) {
        return quizAttemptsRepository.findByQuizIdAndUserIdAndStartTime(quiz.getQuizId(), user.getUserId(), startTime);
    }
}
