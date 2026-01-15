package com.be.controller;

import com.be.dto.AuthDTO;
import com.be.payload.ApiResponse;
import com.be.payload.AuthResponse;
import com.be.payload.LoginResponse;
import com.be.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody AuthDTO register) {

        return ResponseEntity.ok(ApiResponse.success("Register successfully", authService.register(register)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody AuthDTO login, HttpServletResponse response) {

        return ResponseEntity.ok(ApiResponse.success("Login successfully", authService.login(login, response)));
    }
}
