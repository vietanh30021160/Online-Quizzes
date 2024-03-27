package com.swp.online_quizz.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swp.online_quizz.Entity.Classes;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Integer> {
        @Query("SELECT c FROM Classes c WHERE c.classCode = :classCode")
        Classes findbyclassCode(String classCode);

        @Query(value = "select c from Classes c where  c.className like %?1% and c.teacher.userId = ?2")
        List<Classes> searchByClassNameAndUserId(String className, Integer userId);

        @Query(value = "select c from Classes c join Fetch User u on c.teacher.userId = u.userId and c.teacher.userId = ?1 ")
        List<Classes> getAllClassByUserId(Integer userId);

        @Query("select COUNT(c.classId) from Classes c join Fetch User u on c.teacher.userId = u.userId and c.teacher.userId = ?1 ")
        Long getSizeAllClassByUserId(Integer userID);

        @Query(value = "select c from Classes c join Fetch User u on c.teacher.userId = u.userId and c.teacher.userId = ?1 ")
        List<Classes> getAllByTeacherId(Integer userId, Pageable page);

        @Query("SELECT cq FROM Classes cq WHERE LOWER(cq.className) LIKE LOWER(concat('%', :keyword, '%'))")
        List<Classes> findByClassNameContainingIgnoreCase(String keyword);

        @Query("select c from Classes c where c.classId = ?1")
        Classes getClassByClassId(Integer classID);

        // Trong ClassesRepository.java
        @Query("select c.classId from Classes c where c in :classes")
        List<Integer> findClassIdsByClasses(@Param("classes") List<Classes> classes);
}
