package com.swp.online_quizz.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "Note")
    private String note;

    @OneToMany(mappedBy = "message")
    @JsonManagedReference
    private List<MessageRecipient> listMessageRecipient = new ArrayList<>();
}
