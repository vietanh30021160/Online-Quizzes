package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.QuizAttempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAttemptsRepository extends JpaRepository<QuizAttempts,Integer> {
    @Query(value = "select * from QuizAttempts where QuizAttempts.quizId = ?1",nativeQuery = true)
    public List<QuizAttempts> findQuizAttemptsByQuizID(Integer quizID);
    @Query(value = "select qa from QuizAttempts qa join FETCH qa.user u where u.username like %?1%")
    public List<QuizAttempts> searchUseByName(String usename);
}
