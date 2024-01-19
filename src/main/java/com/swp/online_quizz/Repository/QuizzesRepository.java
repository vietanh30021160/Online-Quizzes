package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Quizzes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizzesRepository extends JpaRepository<Quizzes, Integer> {

}
