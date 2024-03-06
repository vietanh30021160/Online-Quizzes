package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.ClassEnrollment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IClassEnrollmentService {

    List<Integer> getClassIdsByStudentId(Integer studentId);

    boolean existsByStudentIdAndClassCode(Integer studentId, String classCode);

    List<ClassEnrollment> getAllStudentByClassId(Integer classID);

    Page<ClassEnrollment> getAllStudentByClassId(Integer classID, Integer page);

    List<ClassEnrollment> getAllStudentBySearch(Integer classID, String firstName);

    Page<ClassEnrollment> getAllStudentBySearch(Integer classID, String firstName, Integer page);
    boolean isAddStudentInClass(ClassEnrollment classEnrollment);
    boolean deleteStudentInClass(Integer id);
}
