package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.QuizAttempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizAttemptsRepository extends JpaRepository<QuizAttempts,Integer> {
}
