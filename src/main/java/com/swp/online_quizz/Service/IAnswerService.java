package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answers;
import org.springframework.stereotype.Service;

@Service
public interface IAnswerService {
    public Answers getAnswers(Integer answerId);
}
