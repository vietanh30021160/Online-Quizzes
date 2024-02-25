package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Message;
import com.swp.online_quizz.Entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Integer> {

}
