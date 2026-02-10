package com.be.payload;

import com.be.enums.GenderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProfileResponse(String displayName, GenderStatus gender, LocalDate dateOfBirth, String image, LocalDateTime updatedAt) {
}
