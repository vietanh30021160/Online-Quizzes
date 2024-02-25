package com.swp.online_quizz.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.MessageRecipient;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.MessageRecipientsRepository;

@Service
public class MessageRecipientsService implements IMessageRecipientsService {
    @Autowired
    private MessageRecipientsRepository messageRecipientRepository;

    @Override
    public List<MessageRecipient> findByRecipient(User recipient) {
        return messageRecipientRepository.findByRecipient(recipient.getUserId());
    }
}
