package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Classes")
public class Classes {
    @Id
    @Column(name = "ClassID", nullable = false)
    private Integer classId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TeacherID")
    private User teacher;

    @Column(name = "ClassName", length = 100)
    private String className;

    @Column(name = "ClassCode", length = 100)
    private String classCode;

}