package com.swp.online_quizz.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp.online_quizz.Entity.Answer;

@Repository
public interface AnswersRepository extends JpaRepository<Answer, Integer> {
}
