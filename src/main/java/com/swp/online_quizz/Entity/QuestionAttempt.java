package com.swp.online_quizz.Entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "QuestionAttempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAttempt implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuestionAttemptID")
    private Integer questionAttemptID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AttemptID")
    private QuizAttempt attempt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QuestionID")
    private Question question;

    @Column(name = "Answer")
    private String answer;

    @Column(name = "IsAnswered")
    private Boolean isAnswered;

    @Column(name = "QuestionOrder")
    private Integer questionOrder;

    @Column(name = "IsCorrect")
    private Boolean isCorrect;

    public QuestionAttempt(QuizAttempt attempt, Question question, String answer, Boolean isAnswered, Integer questionOrder, Boolean isCorrect) {
        this.attempt = attempt;
        this.question = question;
        this.answer = answer;
        this.isAnswered = isAnswered;
        this.questionOrder = questionOrder;
        this.isCorrect = isCorrect;
    }
}
