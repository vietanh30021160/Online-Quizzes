package com.swp.online_quizz.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp.online_quizz.Entity.QuestionAttempt;

@Repository
public interface QuestionAttemptsRepository extends JpaRepository<QuestionAttempt, Integer> {
}
