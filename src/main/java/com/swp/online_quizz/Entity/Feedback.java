package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
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
    @Column(name = "FeedbackID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AttemptID")
    private QuizAttempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private User userID;

    @Lob
    @Column(name = "Comment")
    private String comment;

}
