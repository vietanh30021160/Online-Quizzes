package com.swp.online_quizz.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swp.online_quizz.Entity.MessageRecipient;

@Repository
public interface MessageRecipientsRepository extends JpaRepository<MessageRecipient, Integer> {
    @Query("select m from MessageRecipient m where m.recipient.userId = :recipientID")
    List<MessageRecipient> findByRecipient(@Param("recipientID") Integer recipientID);
}
