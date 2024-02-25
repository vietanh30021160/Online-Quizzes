package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Classes;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IClassesService {
    void joinClass(String classCode, Integer studentId);
    List<Classes> getAll();

    Boolean createClass(Classes classes);

    Classes findById(Integer id);

    Boolean updateClass(Classes classes);

    Boolean deleteClass(Integer id);

    List<Classes> searchClassesByClassesNameAndUserID(String classesName,Integer userId);

    Page<Classes> searchClassesByClassesNameAndUserID(String classesName,Integer pageNo,Integer userId);

    Page<Classes> getAll(Integer pageNo);

    List<Classes> getAllClassByUserId(Integer userID);
    Page<Classes> getAllClassByUserId(Integer userID,Integer userId);
}
