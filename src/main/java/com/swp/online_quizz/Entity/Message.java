package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Messages")
public class Message {
    @Id
    @Column(name = "MessageID", nullable = false)
    private Integer messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SenderID")
    private User sender;

    @Column(name = "MessageType", nullable = false, length = 20)
    private String messageType;

    @Lob
    @Column(name = "MessageContent")
    private String messageContent;

    @Column(name = "SendTime")

    private Timestamp sendTime;

    @Column(name = "IsRead")
    private Boolean isRead;

}
