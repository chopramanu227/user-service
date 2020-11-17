package com.example.user.exception;

public class EmptyPayloadException extends RuntimeException {

    public EmptyPayloadException(String message) {
        super(message);
    }
}
