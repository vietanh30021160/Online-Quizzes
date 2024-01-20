package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
public class LoginHistory {
    @Id
    @Column(name = "LoginHistoryID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private User userID;

    @Column(name = "LoginTime")
    private Instant loginTime;

    @Column(name = "LogoutTime")
    private Instant logoutTime;

}