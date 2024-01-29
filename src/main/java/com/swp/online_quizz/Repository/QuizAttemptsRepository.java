package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAttemptsRepository extends JpaRepository<QuizAttempt,Integer> {
    @Query(value = "select * from QuizAttempts where QuizAttempts.quizId = ?1", nativeQuery = true)
    public List<QuizAttempt> findQuizAttemptsByQuizID(Integer quizID);

    @Query(value = "select qa from QuizAttempt qa join FETCH qa.user u join fetch qa.quiz q where u.username like %?1% and q.quizId = ?2")
    public List<QuizAttempt> searchUseByName(String username, Integer quizID);
}


