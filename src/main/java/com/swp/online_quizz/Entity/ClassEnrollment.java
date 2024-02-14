package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ClassEnrollments")
public class ClassEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EnrollmentID", nullable = false)
    private Integer enrollmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClassID")
    private Classes classes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentID")
    private User studentID;

}