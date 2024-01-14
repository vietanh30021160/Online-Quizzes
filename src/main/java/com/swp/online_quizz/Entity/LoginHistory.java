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
@Table(name = "LoginHistory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistory {
    @Id
    @Column(name = "LoginHistoryID")
    private Integer loginHistoryId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "LoginTime")
    private LocalDateTime loginTime;

    @Column(name = "LogoutTime")
    private LocalDateTime logoutTime;

}
