package com.swp.online_quizz.Interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.swp.online_quizz.Entity.MessageRecipient;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.IMessageRecipientsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MessageHandleInterceptor implements HandlerInterceptor {
    @Autowired
    private IMessageRecipientsService iMessageRecipientsService;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        // Thực hiện load thông điệp
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            List<MessageRecipient> messages = new ArrayList<>();
            request.setAttribute("messages", messages);
            return true;
        }
        // Nếu có thì lấy ra user
        User user = userOptional.get();
        // Load messages before handling the request
        User user1 = new User();
        user1.setUserId(7);
        List<MessageRecipient> messages = iMessageRecipientsService.findByRecipient(user1);
        request.setAttribute("messages", messages);
        return true;
    }
}
