package com.be.ws;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    private final Map<UUID, Set<WebSocketSession>> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    public WebSocketSessionManager(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void add(UUID userId, WebSocketSession session){
        sessions.computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet()).add(session);
    }

    public void remove(UUID userId, WebSocketSession session){
        Set<WebSocketSession> userSessions = sessions.get(userId);
        if (userSessions != null) {
            userSessions.remove(session);
            if (userSessions.isEmpty()) {
                sessions.remove(userId);
            }
        }
    }

    public Set<WebSocketSession> get(UUID userId){
        return sessions.get(userId);
    }
    
    public int getSessionCount(UUID userId) {
        Set<WebSocketSession> userSessions = sessions.get(userId);
        return userSessions != null ? userSessions.size() : 0;
    }

    public void broadcast(Object payload) {
        sessions.values().forEach(userSessions -> {
            userSessions.forEach(session -> {
                try {
                    if (session.isOpen()) {
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(payload)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
