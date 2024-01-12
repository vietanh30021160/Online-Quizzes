package Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "QuizProgress")
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

    public Integer getProgressId() {
        return this.progressId;
    }

    public void setProgressId(Integer progressId) {
        this.progressId = progressId;
    }

    public Integer getAttemptId() {
        return this.attemptId;
    }

    public void setAttemptId(Integer attemptId) {
        this.attemptId = attemptId;
    }

    public Integer getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Boolean getIsAnswered() {
        return this.isAnswered;
    }

    public void setIsAnswered(Boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
