package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    @Query(value = "SELECT *FROM Subject where id = ?1", nativeQuery = true)
    Subject findById(int id);
}
