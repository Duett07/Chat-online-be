package com.be.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "messages_status")
@Data
public class MessagesStatus extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "message_id")
    private Messages messages;

    @Enumerated(EnumType.STRING)
    private com.be.enums.MessagesStatus status;
}
