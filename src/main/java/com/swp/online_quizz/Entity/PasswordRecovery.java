package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
public class PasswordRecovery {
    @Id
    @Column(name = "RecoveryID", nullable = false)
    private Integer recoveryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "Token", length = 100)
    private String token;

    @Column(name = "ExpiryTime")
    private Instant expiryTime;

}