package com.swp.online_quizz.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp.online_quizz.Entity.Answer;

import java.util.List;

@Repository
public interface AnswersRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestionQuestionId(Integer questionId);
}
