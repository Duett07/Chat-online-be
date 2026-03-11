package com.be.payload;

import com.be.enums.GenderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(UUID id, String displayName, GenderStatus gender, LocalDate dateOfBirth, String image, LocalDateTime updatedAt) {
}
