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
@Table(name = "Messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Messages {
    @Id
    @Column(name = "MessageID")
    private Integer messageId;

    @Column(name = "SenderID")
    private Integer senderId;

    @Column(name = "MessageType")
    private String messageType;

    @Column(name = "MessageContent")
    private String messageContent;

    @Column(name = "SendTime")
    private LocalDateTime sendTime;

    @Column(name = "IsRead")
    private Boolean isRead;

}
