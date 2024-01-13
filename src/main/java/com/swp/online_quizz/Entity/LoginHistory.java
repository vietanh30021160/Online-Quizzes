package com.swp.online_quizz.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LoginHistory")
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

    public Integer getLoginHistoryId() {
        return this.loginHistoryId;
    }

    public void setLoginHistoryId(Integer loginHistoryId) {
        this.loginHistoryId = loginHistoryId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDateTime getLogoutTime() {
        return this.logoutTime;
    }

    public void setLogoutTime(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }
}
