package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.ClassEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassEnrollmentRepository extends JpaRepository<ClassEnrollment,Integer> {
    @Query("SELECT ce FROM ClassEnrollment ce WHERE ce.studentID.userId = :studentID")
    List<ClassEnrollment> findbystudentID(Integer studentID);
    boolean existsByStudentID_UserIdAndClasses_ClassCode(Integer userId, String classCode);
}

