package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.QuizProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizProgressRepository extends JpaRepository<QuizProgress, Integer> {
}
