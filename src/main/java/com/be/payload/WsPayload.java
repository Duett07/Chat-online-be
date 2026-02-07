package com.be.payload;

public record WsPayload<T>(String type, T data) {
    public static WsPayload<MessageResponse> message(MessageResponse response) {
        return new WsPayload<>("MESSAGE", response);
    }
}
