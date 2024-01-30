package com.swp.online_quizz.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "Username")
    private String username;

    @Column(name = "PasswordHash")
    private String passwordHash;

    @Column(name = "Email")
    private String email;

    @Column(name = "Role")
    private String role;

    @Column(name = "JoinDate")
    private LocalDateTime joinDate;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "Address")
    private String address;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "ProfilePictureURL")
    private String profilePictureUrl;

    @Column(name = "IsActive")
    private Boolean isActive;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<QuizAttempt> listQuizAttempts;
}
