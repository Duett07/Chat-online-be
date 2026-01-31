package com.be.ws;

import com.be.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
@AllArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if (!(request instanceof ServletServerHttpRequest servletRequest)) {
            return false;
        }

        HttpServletRequest httpRequest = servletRequest.getServletRequest();

        Cookie[] cookies = httpRequest.getCookies();
        if (cookies == null) return false;

        String token = null;
        for (Cookie cookie : cookies) {
            if ("accessToken".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }

        if (token == null || !jwtService.validate(token)) {
            return false;
        }

        String userId = jwtService.getUserId(token);
        attributes.put("userId", userId);

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception) {

    }


    private String extractToken(String query) {
        if (query == null) return null;

        return UriComponentsBuilder
                .fromUriString("?" + query)
                .build()
                .getQueryParams()
                .getFirst("token");
    }
}
