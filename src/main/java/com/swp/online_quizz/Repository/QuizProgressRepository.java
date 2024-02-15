package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.QuizProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizProgressRepository extends JpaRepository<QuizProgress, Integer> {
    List<QuizProgress> findByAttemptQuizQuizId(Integer quizId);
}
