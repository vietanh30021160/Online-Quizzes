package com.swp.online_quizz.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Subjects")
public class Subjects {
    @Id
    @Column(name = "SubjectID")
    private Integer subjectId;

    @Column(name = "SubjectName")
    private String subjectName;

    @Column(name = "Description")
    private String description;

    public Integer getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
