package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.ClassEnrollment;
import com.swp.online_quizz.Repository.ClassEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassEnrollmentService implements IClassEnrollmentService{
    @Autowired
    private ClassEnrollmentRepository classEnrollmentRepository;
    @Override
    public List<Integer> getClassIdsByStudentId(Integer studentId) {
        List<ClassEnrollment> enrollments = classEnrollmentRepository.findbystudentID(studentId);
        List<Integer> classIds = new ArrayList<>();
        for (ClassEnrollment enrollment : enrollments) {
            classIds.add(enrollment.getClasses().getClassId());
        }
        return classIds;
    }

    @Override
    public boolean existsByStudentIdAndClassCode(Integer studentId, String classCode) {
        return classEnrollmentRepository.existsByStudentID_UserIdAndClasses_ClassCode(studentId, classCode);
    }
}
