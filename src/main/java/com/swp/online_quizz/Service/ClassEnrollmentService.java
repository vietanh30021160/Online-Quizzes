package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.ClassEnrollment;
import com.swp.online_quizz.Repository.ClassEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassEnrollmentService implements IClassEnrollmentService {
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

    @Override
    public List<ClassEnrollment> getAllStudentByClassId(Integer classID) {
        return this.classEnrollmentRepository.findStudenByClassId(classID);
    }

    @Override
    public Page<ClassEnrollment> getAllStudentByClassId(Integer classID, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 2);
        List<ClassEnrollment> listStudentInClass = this.classEnrollmentRepository.findStudenByClassId(classID, pageable);
        return new PageImpl<ClassEnrollment>(listStudentInClass, pageable, this.classEnrollmentRepository.getSizeAllStudentInClass(classID));
    }

    @Override
    public List<ClassEnrollment> getAllStudentBySearch(Integer classID, String firstName) {
        return this.classEnrollmentRepository.ListStudentBySearch(classID, firstName);
    }

    @Override
    public Page<ClassEnrollment> getAllStudentBySearch(Integer classID, String firstName, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, 2);
        List<ClassEnrollment> listStudentInClassBySearch = this.classEnrollmentRepository.ListStudentBySearch(classID, firstName, pageable);

        return new PageImpl<ClassEnrollment>(listStudentInClassBySearch, pageable, this.classEnrollmentRepository.getSizeListStudentBySearch(classID, firstName));
    }

    @Override
    public boolean isAddStudentInClass(ClassEnrollment classEnrollment) {
        try {
            this.classEnrollmentRepository.save(classEnrollment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteStudentInClass(Integer id) {
        try {
            this.classEnrollmentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
