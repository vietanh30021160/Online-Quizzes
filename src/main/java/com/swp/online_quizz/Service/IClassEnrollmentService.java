package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.ClassEnrollment;

import java.util.List;

public interface IClassEnrollmentService {

    List<Integer> getClassIdsByStudentId(Integer studentId);
    boolean existsByStudentIdAndClassCode(Integer studentId, String classCode);
}
