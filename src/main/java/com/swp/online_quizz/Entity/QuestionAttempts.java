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
@Table(name = "QuestionAttempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAttempts {
    @Id
    @Column(name = "AttemptID")
    private Integer attemptId;

    @Id
    @Column(name = "QuestionID")
    private Integer questionId;

    @Column(name = "Answer")
    private String answer;

    @Column(name = "IsAnswered")
    private Boolean isAnswered;

    @Column(name = "IsCorrect")
    private Boolean isCorrect;
}
