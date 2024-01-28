package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MessageRecipients")
public class MessageRecipient {
    @Id
    @Column(name = "MessageRecipientID", nullable = false)
    private Integer messageRecipientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MessageID")
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RecipientID")
    private User recipient;

}