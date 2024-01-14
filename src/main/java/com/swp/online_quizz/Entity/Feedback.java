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
@Table(name = "Feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @Column(name = "FeedbackID")
    private Integer feedbackId;

    @Column(name = "AttemptID")
    private Integer attemptId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "Comment")
    private String comment;

}
