package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.QuestionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionAttemptsRepository extends JpaRepository<QuestionAttempt,Integer>{
    @Query(value = "select qa from QuestionAttempt qa join FETCH qa.attempt aid where aid.attemptId = ?1")
    public List<QuestionAttempt> findByAttemptID(Integer AttemptID);

    List<QuestionAttempt> findByAttemptQuizQuizId(Integer quizId);
}
