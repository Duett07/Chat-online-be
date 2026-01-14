package com.be.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterResponse {
    private UUID id;
    private String username;
    private LocalDateTime createdAt;
}
