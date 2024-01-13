package com.swp.online_quizz.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Feedback")
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

    public Integer getFeedbackId() {
        return this.feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getAttemptId() {
        return this.attemptId;
    }

    public void setAttemptId(Integer attemptId) {
        this.attemptId = attemptId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
