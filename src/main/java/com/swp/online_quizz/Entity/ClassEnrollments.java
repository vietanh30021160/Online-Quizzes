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
@Table(name = "ClassEnrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassEnrollments {
    @Id
    @Column(name = "EnrollmentID")
    private Integer enrollmentId;

    @Column(name = "ClassID")
    private Integer classId;

    @Column(name = "StudentID")
    private Integer studentId;

}
