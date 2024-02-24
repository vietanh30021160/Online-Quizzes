package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.ClassEnrollment;
import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.User;

import com.swp.online_quizz.Repository.ClassEnrollmentRepository;
import com.swp.online_quizz.Repository.ClassesRepository;
import com.swp.online_quizz.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassesService implements IClassesService {

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private ClassEnrollmentRepository classEnrollmentRepository;
    @Override
    public void joinClass(String classCode, Integer studentId) {

        // Tìm lớp học dựa trên classCode
        Classes classes = classesRepository.findbyclassCode(classCode);

        if (classes == null) {
            // Xử lý trường hợp không tìm thấy lớp học với classCode cụ thể
            throw new RuntimeException("Class not found with classCode: " + classCode);
        }

        // Tạo đối tượng User đại diện cho học sinh
        User student = new User();
        student.setUserId(studentId);

        // Tạo bản ghi ClassEnrollment
        ClassEnrollment classEnrollment = new ClassEnrollment();
        classEnrollment.setClasses(classes);
        classEnrollment.setStudentID(student);

        // Lưu bản ghi ClassEnrollment vào cơ sở dữ liệu
        classEnrollmentRepository.save(classEnrollment);
    }
}
