package com.swp.online_quizz.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MessageRecipients")
public class MessageRecipients {
    @Id
    @Column(name = "MessageRecipientID")
    private Integer messageRecipientId;

    @Column(name = "MessageID")
    private Integer messageId;

    @Column(name = "RecipientID")
    private Integer recipientId;

    public Integer getMessageRecipientId() {
        return this.messageRecipientId;
    }

    public void setMessageRecipientId(Integer messageRecipientId) {
        this.messageRecipientId = messageRecipientId;
    }

    public Integer getMessageId() {
        return this.messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getRecipientId() {
        return this.recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }
}
