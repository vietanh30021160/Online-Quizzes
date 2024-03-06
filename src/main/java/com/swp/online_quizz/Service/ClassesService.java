package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.ClassEnrollment;
import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.User;

import com.swp.online_quizz.Repository.ClassEnrollmentRepository;
import com.swp.online_quizz.Repository.ClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

        return this.classesRepository.findById(id).get();
    }

    @Override
    public Boolean updateClass(Classes classes) {
        try {
            this.classesRepository.save(classes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean deleteClass(Integer id) {
        try {
            this.classesRepository.delete(findById(id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Classes> searchClassesByClassesNameAndUserID(String classesName, Integer userId) {
        return this.classesRepository.searchByClassNameAndUserId(classesName, userId);
    }

    @Override
    public Page<Classes> searchClassesByClassesNameAndUserID(String classesName, Integer pageNo, Integer userId) {
        List<Classes> allClasses = this.searchClassesByClassesNameAndUserID(classesName, userId);

        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Integer start = (int) pageable.getOffset();
        Integer end = (start + pageable.getPageSize()) > allClasses.size() ? allClasses.size() : (start + pageable.getPageSize());
        allClasses = allClasses.subList(start, end);
        return new PageImpl<Classes>(allClasses, pageable, this.searchClassesByClassesNameAndUserID(classesName, userId).size());
    }

    @Override
    public Page<Classes> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        return this.classesRepository.findAll(pageable);
    }


    @Override
    public List<Classes> getAllClassByUserId(Integer userID) {
        return this.classesRepository.getAllClassByUserId(userID);
    }

    @Override
    public Page<Classes> getAllClassByUserId(Integer userID, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 2);
        List<Classes> allClasseById = this.classesRepository.getAllByTeacherId(userID, pageable);
//           List<Classes> list = allClasseById.subList((pageNo-1)*2, Integer.min((pageNo-1)*2+2, allClasseById.size()));
        return new PageImpl<Classes>(allClasseById, pageable, classesRepository.getSizeAllClassByUserId(userID));
    }
    @Override
    public List<Classes> searchByClassName(String keyword) {
        List<Classes> result;
        if (keyword != null && !keyword.isEmpty()) {
            result = classesRepository.findByClassNameContainingIgnoreCase(keyword);
        } else {
            result = classesRepository.findAll(); // Lấy tất cả nếu không có từ khóa
        }
        // Kiểm tra xem kết quả trả về có rỗng không
        if (result.isEmpty()) {
            result = classesRepository.findAll(); // Lấy tất cả nếu không có kết quả nào
        }
        return result;
    }
    @Override
    public List<Classes> getClassesByStudentID(Integer studentID){
        List<Classes> getClassesByStudentID = new ArrayList<>();

        // Lấy danh sách các đăng ký lớp học của sinh viên từ ClassEnrollmentRepository
        List<ClassEnrollment> classEnrollments = classEnrollmentRepository.findbystudentID(studentID);

        // Lấy thông tin chi tiết của từng lớp học từ ClassesRepository và thêm vào danh sách enrolledClasses
        for (ClassEnrollment enrollment : classEnrollments) {
            getClassesByStudentID.add(enrollment.getClasses());
        }

        return getClassesByStudentID;
    }


}
