package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Query("SELECT q FROM Quiz q JOIN FETCH q.subject s WHERE s.subjectId = :subjectId")
    public Quiz findQuizzesBySubjectID(@Param("subjectId") int subjectId);
    @Query("select q from Quiz q JOIN fetch q.quizAttempts qa where qa.quiz.quizId = :attemptID")
    Quiz findByAttemptID(Integer attemptID);
}
