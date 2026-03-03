package com.be.controller;

import com.be.dto.SendMessagesRequest;
import com.be.payload.*;
import com.be.service.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
@AllArgsConstructor
public class MessageController {
    private final MessagesService messagesService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<MessageResponse>> sendMessage(@RequestBody SendMessagesRequest request){
        return ResponseEntity.ok(ApiResponse.success("Send message successfully", messagesService.sendMessage(request)));
    }

    @GetMapping("/with/{userId}")
    public ResponseEntity<ApiResponse<List<MessagesRes>>> getMessages(@PathVariable UUID userId){
        return ResponseEntity.ok(ApiResponse.success("Get all messages successfully", messagesService.getAllMessages(userId)));
    }

    @PutMapping("/delete/{messageId}")
    public ResponseEntity<ApiResponse<MessageDeleteRes>> deleteMessage(@PathVariable UUID messageId){

        return ResponseEntity.ok(ApiResponse.success("Delete message successfully", messagesService.deleteMessages(messageId)));
    }
}
