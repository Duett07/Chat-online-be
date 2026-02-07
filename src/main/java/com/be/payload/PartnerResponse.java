package com.be.payload;

import java.util.UUID;

public record PartnerResponse(UUID id, String displayName, boolean online, String image) {
}
