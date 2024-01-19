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
@Table(name = "QuizProgress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizProgress {
    @Id
    @Column(name = "ProgressID")
    private Integer progressId;

    @Column(name = "AttemptID")
    private Integer attemptId;

    @Column(name = "QuestionID")
    private Integer questionId;

    @Column(name = "IsAnswered")
    private Boolean isAnswered;

    @Column(name = "Answer")
    private String answer;

}
