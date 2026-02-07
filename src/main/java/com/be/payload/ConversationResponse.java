package com.be.payload;

import java.util.UUID;

public record ConversationResponse(UUID conversationId, PartnerResponse partner, LastMessageResponse lastMessage) {
}
