package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Quizzes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizzesRepository extends JpaRepository<Quizzes, Integer> {
//    @Query(value = "select q.*,s.SubjectName,s.Description from Quizzes q join FETCH  q.subject  where s.subjectId = ?1" ,nativeQuery = true )
//    public Quizzes findQuizzesBySubjectID(int SubjectId);
    @Query("SELECT q FROM Quizzes q JOIN FETCH q.subject s WHERE s.subjectId = :subjectId")
    public Quizzes findQuizzesBySubjectID(@Param("subjectId") int subjectId);

}
