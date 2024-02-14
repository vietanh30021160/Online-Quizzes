package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByAttemptAttemptId(Integer attemptId);
}
