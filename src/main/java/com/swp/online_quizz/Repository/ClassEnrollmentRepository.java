package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.ClassEnrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassEnrollmentRepository extends JpaRepository<ClassEnrollment, Integer> {
    @Query("SELECT ce FROM ClassEnrollment ce WHERE ce.studentID.userId = :studentID")
    List<ClassEnrollment> findbystudentID(Integer studentID);

    boolean existsByStudentID_UserIdAndClasses_ClassCode(Integer userId, String classCode);

    @Query("select ce from ClassEnrollment ce where ce.classes.classId = ?1 ")
    List<ClassEnrollment> findStudenByClassId(Integer classId);

    @Query("select ce from ClassEnrollment ce where ce.classes.classId = ?1 ")
    List<ClassEnrollment> findStudenByClassId(Integer classId, Pageable page);

    @Query("select COUNT(ce.studentID) from ClassEnrollment ce where ce.classes.classId = ?1 ")
    Long getSizeAllStudentInClass(Integer classId);
    @Query("select ce from ClassEnrollment ce join fetch User u on ce.studentID.userId = u.userId and ce.classes.classId = ?1 and u.firstName like %?2%")
    List<ClassEnrollment> ListStudentBySearch(Integer classId, String firstName);

    @Query("select ce from ClassEnrollment ce join fetch User u on ce.studentID.userId = u.userId and ce.classes.classId = ?1 and u.firstName like %?2%")
    List<ClassEnrollment> ListStudentBySearch(Integer classId, String firstName, Pageable page);

    @Query("select COUNT(ce.studentID) from ClassEnrollment ce join fetch User u on ce.studentID.userId = u.userId and ce.classes.classId = ?1 and u.firstName like %?2%")
    Long getSizeListStudentBySearch(Integer classId, String firstName);
}

