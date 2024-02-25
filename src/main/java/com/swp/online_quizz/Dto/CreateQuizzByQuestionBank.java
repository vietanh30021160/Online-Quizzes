package com.swp.online_quizz.Dto;

import com.swp.online_quizz.Entity.Quiz;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateQuizzByQuestionBank {

    private Quiz quiz;
    private List<Integer> selectedQuestions;
}
