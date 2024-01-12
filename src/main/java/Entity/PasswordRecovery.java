package Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PasswordRecovery")
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

    public Integer getRecoveryId() {
        return this.recoveryId;
    }

    public void setRecoveryId(Integer recoveryId) {
        this.recoveryId = recoveryId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryTime() {
        return this.expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }
}
