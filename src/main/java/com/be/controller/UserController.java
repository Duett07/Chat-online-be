package com.be.controller;

import com.be.dto.UpdateDTO;
import com.be.payload.ApiResponse;
import com.be.payload.ProfileResponse;
import com.be.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile() {

       return ResponseEntity.ok(ApiResponse.success("Get profile successfully", userService.getProfile()));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(@RequestBody UpdateDTO data) {

        return ResponseEntity.ok(ApiResponse.success("Update successfully", userService.updateProfile(data)));
    }

}
