package com.swp.online_quizz.Service;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.Feedback;
import com.swp.online_quizz.Entity.Message;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.User;

@Service
public interface IMessagesService {
    public Message createNotificationNewQuiz(Quiz quiz, Classes classes, User User);

    public Message createNotificationNewAcceptTeacher(User Teacher);

    public Message createNotificationNewFeedback(Feedback feedback);
}
