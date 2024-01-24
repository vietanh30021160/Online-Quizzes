package com.swp.online_quizz.Repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swp.online_quizz.Entity.QuizAttempts;

@Repository
public interface QuizAttemptsRepository extends JpaRepository<QuizAttempts, Integer> {
    @Query("SELECT qa FROM QuizAttempts qa WHERE qa.quiz.quizId = :quizId AND qa.user.userId = :userId")
    List<QuizAttempts> findByQuizIdAndUserId(@Param("quizId") Integer quizId, @Param("userId") Integer userId);

    @Query("SELECT qa FROM QuizAttempts qa WHERE qa.quiz.quizId = :quizId AND qa.user.userId = :userId AND qa.startTime = :startTime")
    List<QuizAttempts> findByQuizIdAndUserIdAndStartTime(@Param("quizId") Integer quizId,
            @Param("userId") Integer userId, @Param("startTime") Timestamp startTime);
}
