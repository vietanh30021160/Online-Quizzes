package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Integer> {
    @Query("SELECT c FROM Classes c WHERE c.classCode = :classCode")
    Classes findbyclassCode(String classCode);

    @Query(value = "select c from Classes c where  c.className like %?1% and c.teacher.userId = ?2")
    List<Classes> searchByClassNameAndUserId(String className ,Integer userId);

    @Query(value = "select c from Classes c join Fetch User u on c.teacher.userId = u.userId and c.teacher.userId = ?1 ")
    List<Classes> getAllClassByUserId(Integer userId);
}
