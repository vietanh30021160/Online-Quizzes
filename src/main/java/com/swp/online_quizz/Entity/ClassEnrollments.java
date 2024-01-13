package com.swp.online_quizz.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ClassEnrollments")
public class ClassEnrollments {
    @Id
    @Column(name = "EnrollmentID")
    private Integer enrollmentId;

    @Column(name = "ClassID")
    private Integer classId;

    @Column(name = "StudentID")
    private Integer studentId;

    public Integer getEnrollmentId() {
        return this.enrollmentId;
    }

    public void setEnrollmentId(Integer enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Integer getClassId() {
        return this.classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
