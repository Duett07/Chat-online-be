package com.be.controller;

import com.be.dto.RegisterDTO;
import com.be.payload.ApiResponse;
import com.be.payload.RegisterResponse;
import com.be.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>> test() {
        return ResponseEntity.ok(ApiResponse.success("Ok ok ok", "Success"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterDTO register) {

        return ResponseEntity.ok(ApiResponse.success("Register successfully", authService.register(register)));
    }
}
