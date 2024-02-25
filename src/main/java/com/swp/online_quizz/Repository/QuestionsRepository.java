package com.swp.online_quizz.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.swp.online_quizz.Entity.Question;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Integer> {
    List<Question> findByQuizQuizId(Integer quizId);

    @Query("SELECT q FROM Question q WHERE (q.questionContent, q.questionId) IN (" +
            "SELECT q2.questionContent, MIN(q2.questionId) FROM Question q2 GROUP BY q2.questionContent)")
    List<Question> getAllQuestionUnique();

    @Query("SELECT q FROM Question q WHERE (q.questionContent, q.questionId) IN (" +
            "SELECT q2.questionContent, MIN(q2.questionId) FROM Question q2 GROUP BY q2.questionContent) and q.questionContent like '%?1%'")
    List<Question> getALlQuestionBySearch(String question);


}
