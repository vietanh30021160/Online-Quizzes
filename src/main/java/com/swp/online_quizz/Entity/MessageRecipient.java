package com.swp.online_quizz.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MessageRecipients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRecipient {
    @Id
    @Column(name = "MessageRecipientID")
    private Integer messageRecipientId;

    @Column(name = "MessageID")
    private Integer messageId;

    @Column(name = "RecipientID")
    private Integer recipientId;

}
