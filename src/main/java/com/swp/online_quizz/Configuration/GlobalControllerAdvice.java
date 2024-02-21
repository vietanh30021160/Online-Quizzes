package com.swp.online_quizz.Configuration;

import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.swp.online_quizz.Entity.MessageRecipient;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("messages")
    public List<MessageRecipient> messages(HttpServletRequest request) {
        return (List<MessageRecipient>) request.getAttribute("messages");
    }
}
