package com.be.ws;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    private final Map<UUID, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void add(UUID userId, WebSocketSession session){
        sessions.put(userId, session);
    }

    public void remove(UUID userId){
        sessions.remove(userId);
    }

    public WebSocketSession get(UUID userId){
        return sessions.get(userId);
    }
}
