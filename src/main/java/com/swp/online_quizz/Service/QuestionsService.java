package com.swp.online_quizz.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.Questions;
import com.swp.online_quizz.Repository.QuestionsRepository;

@Service
public class QuestionsService implements IQuestionsService {
    @Autowired
    QuestionsRepository questionsRepository;

    @Override
    public List<Questions> getAllQuestions() {
        return questionsRepository.findAll();
    }
}
