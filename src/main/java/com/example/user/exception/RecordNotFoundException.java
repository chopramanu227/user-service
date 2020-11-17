package com.example.user.exception;

/**
 * Exception class for handling No record found exceptions.
 */
public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String message) {
        super(message);
    }
}
