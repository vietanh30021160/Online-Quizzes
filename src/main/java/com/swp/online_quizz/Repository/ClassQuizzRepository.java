package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.ClassQuizz;
import com.swp.online_quizz.Entity.Feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassQuizzRepository extends JpaRepository<ClassQuizz, Integer> {
    List<ClassQuizz> findByQuizQuizId(Integer quizId);
    @Query("SELECT cq FROM ClassQuizz cq WHERE cq.classes.classId IN :classIds")
    List<ClassQuizz> findByClassesClassIdIn(List<Integer> classIds);
}
