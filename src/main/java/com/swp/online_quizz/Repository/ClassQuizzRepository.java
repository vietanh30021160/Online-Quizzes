package com.swp.online_quizz.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swp.online_quizz.Entity.ClassQuizz;
import com.swp.online_quizz.Entity.ClassQuizzId;

@Repository
public interface ClassQuizzRepository extends JpaRepository<ClassQuizz, ClassQuizzId> {
    List<ClassQuizz> findByQuizQuizId(Integer quizId);

    @Query("SELECT cq FROM ClassQuizz cq WHERE cq.classes.classId IN :classIds")
    List<ClassQuizz> findByClassesClassIdIn(List<Integer> classIds);
    @Query("SELECT cq FROM ClassQuizz cq WHERE cq.classes.className = :className")
    List<ClassQuizz> findByClassesClassName(String className);
    @Query("SELECT cq from ClassQuizz cq where cq.classes.classId = ?1")
    List<ClassQuizz> findQuizByClassID(Integer classID);
    @Query("select q.quiz.quizId from ClassQuizz q where q.classes.classId = :classId")
    List<Integer> findQuizIdsByClassId(@Param("classId") Integer classId);

}
