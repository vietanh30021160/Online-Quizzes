package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassesRepository extends JpaRepository<Classes, Integer> {
    @Query("SELECT c FROM Classes c WHERE c.classCode = :classCode")
    Classes findbyclassCode(String classCode);


}
