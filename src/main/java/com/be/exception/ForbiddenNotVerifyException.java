package com.be.exception;

public class ForbiddenNotVerifyException extends RuntimeException {
    public ForbiddenNotVerifyException(String message) {
        super(message);
    }
}
