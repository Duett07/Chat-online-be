package com.be.service;

import com.be.dto.SendMessagesRequest;
import com.be.entity.Conversations;
import com.be.entity.Messages;
import com.be.entity.User;
import com.be.enums.MessagesStatus;
import com.be.enums.UserStatus;
import com.be.payload.*;
import com.be.repository.IConversationsRepository;
import com.be.repository.IMessagesRepository;
import com.be.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessagesService {
    private final IConversationsRepository conversationsRepository;
    private final IUserRepository userRepository;
    private final IMessagesRepository messagesRepository;
    private final AuthService authService;
    private final WebSocketService webSocketService;

    private Conversations getOrCreateConversation(UUID u1, UUID u2){
        User user1 = userRepository.findById(u1).orElseThrow(() -> new RuntimeException("User1 not found"));
        User user2 = userRepository.findById(u2).orElseThrow(() -> new RuntimeException("User2 not found"));
        return conversationsRepository.findBetween(user1, user2)
                .orElseGet(() -> {
                    Conversations conversations = new Conversations();
                    conversations.setUser1(user1);
                    conversations.setUser2(user2);
                    return conversationsRepository.save(conversations);
                });
    }

    public MessageResponse sendMessage(SendMessagesRequest request) {
        UUID senderId = authService.getCurrentUserId();

        Conversations conversation = getOrCreateConversation(senderId, request.receiverId());

        User userSender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("User sender not found"));
        User userReceiver = userRepository.findById(request.receiverId()).orElseThrow(() -> new RuntimeException("User receiver not found"));

        Messages message = new Messages();
        message.setConversations(conversation);
        message.setSender(userSender);
        message.setReceiver(userReceiver);
        message.setContent(request.content());
        message.setDisplayOrder(System.currentTimeMillis());

        messagesRepository.save(message);


        MessageResponse response = new MessageResponse(message.getId(), conversation.getId(), senderId, userReceiver.getId(), message.getContent(), message.getDisplayOrder(), MessagesStatus.SENT, message.getCreatedAt());

        Map<String, Object> payload = Map.of(
                "type", "MESSAGE",
                "data", response
        );

        webSocketService.sendToUser(request.receiverId(), payload);


        return response;
    }

    public List<MessagesRes> getAllMessages(UUID otherUserId) {
        UUID currentUserId = authService.getCurrentUserId();

        Conversations conversation = getOrCreateConversation(currentUserId, otherUserId);

        List<Messages> messages = messagesRepository.findAllByConversations(conversation.getId());

        return messages.stream()
                .map(this::toResponse).toList();
    }

    public MessagesRes toResponse(Messages messages) {
        return new MessagesRes(
          messages.getId(),
          messages.getSender().getId(),
          messages.getReceiver().getId(),
          messages.getContent(),
          messages.getDisplayOrder(),
          messages.getCreatedAt()
        );
    }

}
