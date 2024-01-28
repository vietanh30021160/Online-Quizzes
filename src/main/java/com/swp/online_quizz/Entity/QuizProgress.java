package com.swp.online_quizz.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private QuizAttempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuestionID")
    @JsonBackReference
    private Question question;

    @Column(name = "IsAnswered")
    private Boolean isAnswered;

    @Column(name = "QuestionOrder")
    private Integer questionOrder;

    @Column(name = "Answer")
    private String answer;

    public QuizProgress(QuizAttempt attempt, Question question, Boolean isAnswered, Integer questionOrder,
                        String answer) {
        this.attempt = attempt;
        this.question = question;
        this.isAnswered = isAnswered;
        this.questionOrder = questionOrder;
        this.answer = answer;
    }
}
