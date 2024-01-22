package com.swp.online_quizz.Entity;

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
@Table(name = "Feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @Column(name = "FeedbackID")
    private Integer feedbackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AttemptID")
    @JsonBackReference
    private QuizAttempts attempt;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "Comment")
    private String comment;

}
