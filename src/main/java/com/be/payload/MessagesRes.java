package com.be.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagesRes {
    private UUID id;
    private UUID senderId;
    private UUID receiverId;
    private String content;
    private long displayOrder;
    private LocalDateTime createdAt;
}
