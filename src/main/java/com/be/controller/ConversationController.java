package com.be.controller;

import com.be.payload.ApiResponse;
import com.be.payload.ConversationResponse;
import com.be.service.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/conversations")
@AllArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<ConversationResponse>>> getMyConversations(){

        return ResponseEntity.ok(ApiResponse.success("Get conversations successfully", conversationService.getMyConversations()));
    }
}
