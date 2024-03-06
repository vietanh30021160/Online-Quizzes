package com.swp.online_quizz.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ClassID")
    private Classes classes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StudentID")
    private User studentID;


}