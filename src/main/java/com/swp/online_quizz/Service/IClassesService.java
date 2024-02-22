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

    List<Classes> searchClassesByClassesName(String classesName);

    Page<Classes> getAll(Integer pageNo);
}
