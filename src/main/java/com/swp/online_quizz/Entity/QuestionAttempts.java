package com.swp.online_quizz.Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
public class QuestionAttempts implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AttemptID")
    @JsonBackReference
    private QuizAttempts attempt;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuestionID")
    @JsonBackReference
    private Questions question;

    @Column(name = "Answer")
    private String answer;

    @Column(name = "IsAnswered")
    private Boolean isAnswered;

    @Column(name = "IsCorrect")
    private Boolean isCorrect;
}
