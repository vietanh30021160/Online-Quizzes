package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer>, JpaSpecificationExecutor<Quiz> {
    @Query("SELECT q FROM Quiz q JOIN FETCH q.subject s WHERE s.subjectId = :subjectId")
    public Quiz findQuizzesBySubjectID(@Param("subjectId") int subjectId);
    @Query("select q from Quiz q JOIN fetch q.quizAttempts qa where qa.quiz.quizId = :attemptID")
    Quiz findByAttemptID(Integer attemptID);
    @Query("SELECT q FROM Quiz q WHERE LOWER(q.quizName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(q.subject.subjectName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Quiz> findByKeywordContainingIgnoreCase(@Param("keyword") String keyword);

    List<Quiz> findBytimeLimitBetween(Integer min, Integer max);
    Optional<Quiz> findByQuizId(Integer quizId);
    List<Quiz>  findByTeacherUserId(Integer userId);

}
