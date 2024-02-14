package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.QuizAttempt;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface QuizAttemptsRepository extends JpaRepository<QuizAttempt,Integer> {
    List<QuizAttempt> findByQuizQuizId(Integer quizId);
    @Query(value = "SELECT qa FROM QuizAttempt qa WHERE qa.quiz.quizId = ?1")
    public List<QuizAttempt> findQuizAttemptsByQuizID(Integer quizID, Sort sort);

    @Query(value = "select qa from QuizAttempt qa join FETCH qa.user u join fetch qa.quiz q where u.username like %?1% and q.quizId = ?2")
    public List<QuizAttempt> searchUseByName(String username, Integer quizID);
    @Query("SELECT qa FROM QuizAttempt qa WHERE qa.quiz.quizId = :quizId AND qa.user.userId = :userId")
    List<QuizAttempt> findByQuizIdAndUserId(@Param("quizId") Integer quizId, @Param("userId") Integer userId);

    @Query("SELECT qa FROM QuizAttempt qa WHERE qa.quiz.quizId = :quizId AND qa.user.userId = :userId AND qa.startTime >= :startTime")
    List<QuizAttempt> findByQuizIdAndUserIdAndStartTime(@Param("quizId") Integer quizId,
                                                        @Param("userId") Integer userId, @Param("startTime") Timestamp startTime);
}


