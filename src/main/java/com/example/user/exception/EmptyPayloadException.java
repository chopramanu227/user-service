package com.example.user.exception;

/**
 * Exception class to handle empty payload exception.
 */
public class EmptyPayloadException extends RuntimeException {

    public EmptyPayloadException(String message) {
        super(message);
    }
}
