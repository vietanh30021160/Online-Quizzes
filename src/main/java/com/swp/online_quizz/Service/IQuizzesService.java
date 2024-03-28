package com.swp.online_quizz.Service;

import java.util.List;

import com.swp.online_quizz.Entity.Classes;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;

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
    public Quiz getOneQuiz(Integer quizId);

    public List<Quiz> getAllQuizzes();

    Page<Quiz> searchAndFilterAndSubjectAndQuizIds(String keyword, Integer pageNo, Integer min, Integer max,
            String subject, List<Integer> quizIds, String ClassName);

    Page<Quiz> searchAndFilterAndSubjectForQuizzesNoClass(String keyword, Integer pageNo, Integer min, Integer max,
            String subject, String ClassName);

    Page<Quiz> searchAndFilterAndSubject(String keyword, Integer pageNo, Integer min, Integer max, String subject);

    // Page<Quiz> searchAndFilterAndSubjectAndQuizIds(String keyword, Integer
    // pageNo, Integer min, Integer max,
    // String subject, List<Integer> quizIds);

    Page<Quiz> CombineQuizzes(String keyword, Integer pageNo, Integer min, Integer max, String subject,
            List<Integer> quizIds, String className);

    boolean checkUserAndQuiz(List<Classes> listClassesInUser, Integer quizId);
}
