package com.swp.online_quizz.Service;

import java.util.List;

import com.swp.online_quizz.Entity.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Repository.QuestionsRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionsService implements IQuestionsService {
    @Autowired
    QuestionsRepository questionsRepository;
    @Autowired
    @Lazy
    private QuizService quizService;
    @Override
    public List<Question> getAllQuestions() {
        return questionsRepository.findAll();
    }

    @Override
    public Question getQuestions(Integer questionId) {
        return questionsRepository.getReferenceById(questionId);
    }
    @Transactional
    @Override
    public boolean createQuestion1(Question question) {
        try {
            Quiz existingQuiz = quizService.findQuizById(question.getQuiz().getQuizId());
            question.setQuiz(existingQuiz);
            questionsRepository.save(question);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Question getQuestionById(Integer questionId) {
        return questionsRepository.findById(questionId).orElse(null);
    }

    @Override
    public Question findQuestionById(Integer questionId) {
        return questionsRepository.getReferenceById(questionId);
    }
    @Transactional
    @Override
    public Boolean updateQuestion1(Integer id, Question question) {
        try {
            Question uQuestion = questionsRepository.getReferenceById(id);
            uQuestion.setQuestionContent(question.getQuestionContent());
            uQuestion.setQuestionType(question.getQuestionType());
            uQuestion.setImageUrl(question.getImageUrl());
            uQuestion.setVideoUrl(question.getVideoUrl());
            this.questionsRepository.save(uQuestion);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    @Override
    public List<Question> getQuestionsByQuizId(Integer quizId) {
        return questionsRepository.findByQuizQuizId(quizId);
    }
    @Override
    @Transactional
    public void deleteQuestionsByQuizId(Integer quizId) {
        List<Question> questions = questionsRepository.findByQuizQuizId(quizId);
        questionsRepository.deleteAll(questions);
    }

}
