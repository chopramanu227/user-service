package com.example.user.controller;

import com.example.user.exception.EmptyPayloadException;
import com.example.user.exception.RecordNotFoundException;
import com.example.user.model.Error;
import com.example.user.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * Class provides API exception handling based on different exception types.
 */
@ControllerAdvice
@Slf4j
public class UserControllerAdvise extends ResponseEntityExceptionHandler {

    public static final String VALIDATION_ERROR_INVALID_INPUT_PARAM_FORMAT = "VE51";

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        log.error("ConstraintViolationException : stack trace - {}", ex);
        ErrorResponse error = ErrorResponse.builder()
                .error(Error.builder()
                        .code(VALIDATION_ERROR_INVALID_INPUT_PARAM_FORMAT)
                        .msg(ex.getMessage())
                        .build())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<Object> handleMethodArgumentNotValidException(HttpServletResponse res, MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException : stack trace - {}", ex);
        ErrorResponse error = ErrorResponse.builder()
                .error(Error.builder()
                        .code(VALIDATION_ERROR_INVALID_INPUT_PARAM_FORMAT)
                        .msg(ex.getMessage())
                        .build())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }*/
    @ExceptionHandler(EmptyPayloadException.class)
    @ResponseBody
    public ResponseEntity<Object> handleEmptyPayloadException(EmptyPayloadException ex, WebRequest request) {
        log.error("EmptyPayloadException : stack trace - {}", ex);
        ErrorResponse error = ErrorResponse.builder()
                .error(Error.builder()
                        .code(VALIDATION_ERROR_INVALID_INPUT_PARAM_FORMAT)
                        .msg(ex.getMessage())
                        .build())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { RecordNotFoundException.class })
    @ResponseBody
    protected ResponseEntity<Object> handleRecordNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
