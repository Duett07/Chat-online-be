package com.be.payload;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConversationDeleteRes(UUID conversationId, UUID userId1, UUID userId2, LocalDateTime deletedAtUser1, LocalDateTime deletedAtUser2) {
}
