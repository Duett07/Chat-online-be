package com.be.payload;

import java.time.LocalDateTime;
import java.util.UUID;

public record LastMessageResponse(String content, LocalDateTime createdAt, UUID senderId) {
}
