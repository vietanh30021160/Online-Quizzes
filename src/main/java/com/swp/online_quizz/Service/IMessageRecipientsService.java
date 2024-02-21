package com.swp.online_quizz.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.MessageRecipient;
import com.swp.online_quizz.Entity.User;

@Service
public interface IMessageRecipientsService {
    public List<MessageRecipient> findByRecipient(User recipient);
}
