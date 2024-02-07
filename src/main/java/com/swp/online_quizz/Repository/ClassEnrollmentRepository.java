package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.ClassEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassEnrollmentRepository extends JpaRepository<ClassEnrollment,Integer> {
}
