package com.swp.online_quizz.Service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.ClassEnrollment;
import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.Message;
import com.swp.online_quizz.Entity.MessageRecipient;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.MessageRecipientsRepository;
import com.swp.online_quizz.Repository.MessagesRepository;
import com.swp.online_quizz.Repository.UsersRepository;

@Service
public class MessagesService implements IMessagesService {
    @Autowired
    MessagesRepository messagesRepository;
    @Autowired
    private MessageRecipientsRepository messageRecipientsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public Message createNotificationNewQuiz(Quiz quiz, Classes classes, User teacher) {
        Message message = new Message();
        message.setMessageType("teacher");
        message.setMessageContent("A new quiz titled '" + quiz.getQuizName()
                + "' will be organized for students of class " + classes.getClassName());
        message.setSender(teacher);
        message.setNote("ClassID: " + classes.getClassId() + ", QuizID: " + quiz.getQuizId());
        message.setSendTime(new Timestamp(System.currentTimeMillis()));
        message.setIsRead(false);
        message = messagesRepository.save(message);
        List<ClassEnrollment> listClassEnrol = classes.getListEnrollment();
        for (ClassEnrollment classEnrollment : listClassEnrol) {
            message.getListMessageRecipient().add(new MessageRecipient(message, classEnrollment.getStudentID(), false));
        }
        message = messagesRepository.save(message);
        return message;
    }

    @Override
    public Message createNotificationNewAcceptTeacher(User teacher) {
        Message message = new Message();
        message.setMessageType("system");
        message.setMessageContent("A new teacher account named '" + teacher.getFirstName() + " " + teacher.getLastName()
                + "' is awaiting confirmation");
        message.setSender(teacher);
        message.setNote("AccID: " + teacher.getUserId());
        message.setSendTime(new Timestamp(System.currentTimeMillis()));
        message.setIsRead(false);
        message = messagesRepository.save(message);
        List<User> listAdmin = usersRepository.findByRole("ROLE_ADMIN");
        for (User user : listAdmin) {
            message.getListMessageRecipient().add(messageRecipientsRepository.save(
                    new MessageRecipient(message, user, false)));
        }
        message = messagesRepository.save(message);
        return message;
    }
}
