package com.be.config;

import com.be.ws.ChatWebSocketHandler;
import com.be.ws.JwtHandshakeInterceptor;
import com.be.ws.WebSocketSessionManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import tools.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandler chatWebSocketHandler;
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
    private final WebSocketSessionManager webSocketSessionManager;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/ws").addInterceptors(jwtHandshakeInterceptor).setAllowedOrigins("http://localhost:3000");
    }

    @Bean
    public WebSocketHandler chatHandler() {
        return new ChatWebSocketHandler(webSocketSessionManager);
    }
}
