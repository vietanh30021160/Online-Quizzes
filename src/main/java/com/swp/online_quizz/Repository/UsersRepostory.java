package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepostory extends JpaRepository<Users,Integer> {

}
