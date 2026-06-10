package com.be.ws;

import com.be.entity.User;
import com.be.enums.UserStatus;
import com.be.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager webSocketSessionManager;
    private final IUserRepository userRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        UUID userId = (UUID) session.getAttributes().get("userId");
        boolean isFirstSession = webSocketSessionManager.getSessionCount(userId) == 0;
        webSocketSessionManager.add(userId, session);
        System.out.println("User online: " + userId);
        
        if (isFirstSession) {
            userRepository.findById(userId).ifPresent(user -> {
                user.setStatus(UserStatus.Online);
                userRepository.save(user);
                broadcastUserStatus(userId, true);
            });
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        UUID userId = (UUID) session.getAttributes().get("userId");
        webSocketSessionManager.remove(userId, session);
        System.out.println("User offline: " + userId);
        
        if (webSocketSessionManager.getSessionCount(userId) == 0) {
            userRepository.findById(userId).ifPresent(user -> {
                user.setStatus(UserStatus.Offline);
                userRepository.save(user);
                broadcastUserStatus(userId, false);
            });
        }
    }
    
    private void broadcastUserStatus(UUID userId, boolean online) {
        Map<String, Object> payload = Map.of(
            "type", "USER_STATUS",
            "data", Map.of(
                "userId", userId,
                "online", online
            )
        );
        webSocketSessionManager.broadcast(payload);
    }

}
