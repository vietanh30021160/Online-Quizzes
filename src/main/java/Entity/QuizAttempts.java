package Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "QuizAttempts")
public class QuizAttempts {
    @Id
    @Column(name = "AttemptID")
    private Integer attemptId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "QuizID")
    private Integer quizId;

    @Column(name = "StartTime")
    private LocalDateTime startTime;

    @Column(name = "EndTime")
    private LocalDateTime endTime;

    @Column(name = "Marks")
    private Integer marks;

    @Column(name = "IsCompleted")
    private Boolean isCompleted;

    @Column(name = "CurrentQuestionID")
    private Integer currentQuestionId;

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

    public Integer getQuizId() {
        return this.quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getMarks() {
        return this.marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Boolean getIsCompleted() {
        return this.isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getCurrentQuestionId() {
        return this.currentQuestionId;
    }

    public void setCurrentQuestionId(Integer currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }
}
