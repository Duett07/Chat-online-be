package com.be.dto;

import java.util.UUID;

public record SendMessagesRequest(UUID receiverId, String content) {
}
