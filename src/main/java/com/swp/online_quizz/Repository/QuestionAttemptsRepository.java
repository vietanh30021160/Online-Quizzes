package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.QuestionAttempts;
import com.swp.online_quizz.Entity.QuizAttempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionAttemptsRepository extends JpaRepository<QuestionAttempts,Integer>{
    @Query(value = "select qa from QuestionAttempts qa join FETCH qa.attempt aid where aid.attemptId = ?1")
    public List<QuestionAttempts> findByAttemptID(Integer AttemptID);
}
