package com.swp.online_quizz.Service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.Message;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.MessagesRepository;

@Service
public class MessagesService implements IMessagesService {
    @Autowired
    MessagesRepository messagesRepository;

    @Override
    public Message createNotificationNewQuiz(Quiz quiz, Classes classes, User teacher) {
        Message message = new Message();
        message.setMessageType("New quiz");
        message.setMessageContent("A new quiz titled '" + quiz.getQuizName()
                + "' will be organized for students of class " + classes.getClassName());
        message.setSender(teacher);
        message.setNote("ClassID: " + classes.getClassId() + ", QuizID: " + quiz.getQuizId());
        message.setSendTime(new Timestamp(System.currentTimeMillis()));
        message.setIsRead(false);
        message = messagesRepository.save(message);

        return message;
    }

    @Override
    public Message createNotificationNewAcceptTeacher(User Teacher) {
        return null;
    }
}
