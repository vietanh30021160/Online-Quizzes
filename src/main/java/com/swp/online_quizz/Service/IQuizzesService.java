package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IQuizzesService {
    List<Quiz> getAll();

    List<Quiz> getQuizByUserId(Integer userId);

    public Subject find(Integer quizId);
    public Quiz findByID(Integer quizID);


    Quiz findQuizById(Integer quizId);

    boolean createQuiz1(Quiz quiz);

    Quiz getEmptyQuiz();

    @Transactional
    Boolean updateQuizByQuizId1(Integer id, Quiz quiz);

    @Transactional
    void deleteQuizById(Integer quizId);

    public List<Quiz> searchQuizzes(String keyword);

    Page<Quiz> getAll(Integer pageNo);

    public Page<Quiz> searchQuizzes(String keyword, Integer pageNo);

    // lấy ra quizz
    public Quiz getOneQuizz(Integer quizId);

    public List<Quiz> getAllQuizzes();

    Page<Quiz> searchAndFilterAndSubject(String keyword, Integer pageNo, Integer min, Integer max, String subject);


    Page<Quiz> searchAndFilterAndSubjectAndQuizIds(String keyword, Integer pageNo, Integer min, Integer max, String subject, List<Integer> quizIds);
}



