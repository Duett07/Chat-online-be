package com.be.payload;

import com.be.enums.MessagesStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private UUID id;
    private UUID conversationId;
    private UUID senderId;
    private UUID receiverId;
    private String content;
    private long displayOrder;
    @Enumerated(EnumType.STRING)
    private MessagesStatus status;
    private LocalDateTime createdAt;
}
