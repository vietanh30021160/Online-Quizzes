package com.swp.online_quizz.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PasswordRecovery")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRecovery {
    @Id
    @Column(name = "RecoveryID")
    private Integer recoveryId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "Token")
    private String token;

    @Column(name = "ExpiryTime")
    private LocalDateTime expiryTime;

}
