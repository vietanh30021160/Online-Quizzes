package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Answer;
import org.springframework.stereotype.Service;

public interface IAnswerService {
    public Answer getAnswers(Integer answerId);
}
