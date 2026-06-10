package com.be.service;

import com.be.ws.WebSocketSessionManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;
import java.util.Set;

@Service
@AllArgsConstructor
public class WebSocketService {
    private final WebSocketSessionManager webSocketSessionManager;
    private final ObjectMapper objectMapper;

    public void sendToUser(UUID userId, Object payload) {
        Set<WebSocketSession> userSessions = webSocketSessionManager.get(userId);
        System.out.println("WS send to " + userId + " sessions = " + userSessions);
        if(userSessions == null || userSessions.isEmpty()){
            System.out.println("❌ No WS session for user " + userId);
            return;
        }

        userSessions.forEach(session -> {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(payload)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
