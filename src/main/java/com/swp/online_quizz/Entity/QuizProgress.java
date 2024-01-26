package com.swp.online_quizz.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "QuizProgress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProgressID")
    private Integer progressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AttemptID")
    @JsonBackReference
    private QuizAttempts attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuestionID")
    @JsonBackReference
    private Questions question;

    @Column(name = "IsAnswered")
    private Boolean isAnswered;

    @Column(name = "QuestionOrder")
    private Integer questionOrder;

    @Column(name = "Answer")
    private String answer;

    public QuizProgress(QuizAttempts attempt, Questions question, Boolean isAnswered, Integer questionOrder, String answer) {
        this.attempt = attempt;
        this.question = question;
        this.isAnswered = isAnswered;
        this.questionOrder = questionOrder;
        this.answer = answer;
    }
}
