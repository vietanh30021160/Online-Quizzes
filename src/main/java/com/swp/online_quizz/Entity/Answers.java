package com.swp.online_quizz.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answers {
    @Id
    @Column(name = "AnswerID")
    private Integer answerId;

    @Column(name = "QuestionID")
    private Integer questionId;

    @Column(name = "AnswerContent")
    private String answerContent;

    @Column(name = "IsCorrect")
    private Boolean isCorrect;

}
