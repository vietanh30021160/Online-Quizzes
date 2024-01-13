package com.swp.online_quizz.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Answers")
public class Answers {
    @Id
    @Column(name = "AnswerID")
    private Integer answerId;

    @Column(name = "QuestionID")
    private Integer questionId;

    @Column(name = "AnswerContent")
    private String answerContent;

    @Column(name = "IsCorrect")
    private Boolean isCorrect;

    public Integer getAnswerId() {
        return this.answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getAnswerContent() {
        return this.answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Boolean getIsCorrect() {
        return this.isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
