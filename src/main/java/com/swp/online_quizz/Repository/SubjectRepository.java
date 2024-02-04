package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    @Query(value = "SELECT *FROM Subjects where id = ?1", nativeQuery = true)
    Subject findById(int id);
    Optional<Subject> findBySubjectName(String name);
    @Transactional
    @Modifying
    @Query("UPDATE Subject s SET s.subjectName = :newSubjectName, s.description = :newDescription " +
            "WHERE s.subjectName = :subjectName")
    void updateSubjectBySubjectName(@Param("subjectName") String subjectName,
                                    @Param("newSubjectName") String newSubjectName,
                                    @Param("newDescription") String newDescription);
}
