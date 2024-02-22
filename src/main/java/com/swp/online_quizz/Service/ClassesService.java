package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.ClassEnrollment;
import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.User;

import com.swp.online_quizz.Repository.ClassEnrollmentRepository;
import com.swp.online_quizz.Repository.ClassesRepository;
import com.swp.online_quizz.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesService implements IClassesService {

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private ClassEnrollmentRepository classEnrollmentRepository;

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

    @Override
    public List<Classes> getAll() {
        return this.classesRepository.findAll();
    }

    @Override
    public Boolean createClass(Classes classes) {
        try {
            this.classesRepository.save(classes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Classes findById(Integer id) {
        return null;
    }

    @Override
    public Boolean updateClass(Classes classes) {
        return null;
    }

    @Override
    public Boolean deleteClass(Integer id) {
        return null;
    }

    @Override
    public List<Classes> searchClassesByClassesName(String classesName) {
        return this.classesRepository.searchByClassName(classesName);
    }

    @Override
    public Page<Classes> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,5);
        return this.classesRepository.findAll(pageable);
    }
}
