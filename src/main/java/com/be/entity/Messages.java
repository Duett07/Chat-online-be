package com.be.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "messages")
@Data
public class Messages extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversations conversations;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private String content;
    private long displayOrder;

    @OneToOne(mappedBy = "messages")
    private MessagesStatus status;

    private boolean isDeleted = false;
}
