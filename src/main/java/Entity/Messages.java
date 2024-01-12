package Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Messages")
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

    public Integer getMessageId() {
        return this.messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getSenderId() {
        return this.senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return this.messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}
