package com.be.dto;


import java.time.LocalDate;

public record UpdateDTO(String displayName, String gender, LocalDate dateOfBirth) {
}
