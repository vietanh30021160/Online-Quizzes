package com.swp.online_quizz.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
}