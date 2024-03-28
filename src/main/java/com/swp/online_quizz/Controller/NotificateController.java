package com.swp.online_quizz.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp.online_quizz.Entity.Message;
import com.swp.online_quizz.Entity.MessageRecipient;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.MessageRecipientsRepository;
import com.swp.online_quizz.Service.IMessageRecipientsService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/notif")
public class NotificateController {
    @Autowired
    private MessageRecipientsRepository messageRecipientsRepository;
    @Autowired
    private IMessageRecipientsService iMessageRecipientsService;

    @GetMapping("/mess")
    public String getMethodName(Model model, Authentication auth, HttpServletRequest request) {
        User user = new User();
        user.setUserId(7);
        Message messages = iMessageRecipientsService.findByRecipient(user).get(0).getMessage();
        model.addAttribute("messages", messages);
        return "HomeHeader";
    }

    @GetMapping("/updateSeenStatus/{messageRecipientId}")
    public String updateNotificateStatus(@PathVariable Integer messageRecipientId, Model model) {
        MessageRecipient messageRecipient = messageRecipientsRepository.getReferenceById(messageRecipientId);
        if (messageRecipient.getIsSeen()) {
            messageRecipient.setIsSeen(false);
            messageRecipientsRepository.save(messageRecipient);
        } else {
            messageRecipient.setIsSeen(true);
            messageRecipientsRepository.save(messageRecipient);
        }
        
        return "HomeHeader";
    }

    @GetMapping("/updateIsSeen/{messageRecipientId}")
    public String getNotificateIsSeen(@PathVariable Integer messageRecipientId, Model model) {
        MessageRecipient messageRecipient = messageRecipientsRepository.getReferenceById(messageRecipientId);
        messageRecipient.setIsSeen(true);
        messageRecipientsRepository.save(messageRecipient);
        return "HomeHeader";
    }
}
