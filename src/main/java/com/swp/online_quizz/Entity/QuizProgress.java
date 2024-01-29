package com.swp.online_quizz.Entity;

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
    @Column(name = "ProgressID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AttemptID")
    private QuizAttempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuestionID")
    private Question question;

    @Column(name = "IsAnswered")
    private Boolean isAnswered;

    @Column(name = "Answer")
    private String answer;
}
