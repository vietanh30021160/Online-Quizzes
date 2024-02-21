package com.swp.online_quizz.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MessageRecipients")
@NoArgsConstructor
@AllArgsConstructor
public class MessageRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MessageRecipientID", nullable = false)
    private Integer messageRecipientId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MessageID")
    @JsonBackReference
    private Message message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RecipientID")
    @JsonBackReference
    private User recipient;

    @Column(name = "IsSeen")
    private Boolean IsSeen;

    public MessageRecipient(Message message, User recipient, Boolean isSeen) {
        this.message = message;
        this.recipient = recipient;
        IsSeen = isSeen;
    }
}