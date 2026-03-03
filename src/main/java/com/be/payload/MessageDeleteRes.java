package com.be.payload;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageDeleteRes(UUID messageId, LocalDateTime updatedAt, boolean isDeleted) {
}
