package com.example.user.exception;

import com.example.user.model.Error;
import com.example.user.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class provides API exception handling based on different exception types.
 */
@ControllerAdvice
@Slf4j
public class UserControllerAdvise extends ResponseEntityExceptionHandler {

    private static final String VALIDATION_ERROR_INVALID_INPUT_PARAM_FORMAT = "VE51";
    private static final String VALIDATION_ERROR_RECORD_NOT_FOUND = "VE52";
    private static final String VALIDATION_FAILED = "Validation Failed";

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
        log.error("RecordNotFoundException : stack trace - {}", ex);
        ErrorResponse error = ErrorResponse.builder()
                .error(Error.builder()
                        .code(VALIDATION_ERROR_RECORD_NOT_FOUND)
                        .msg(ex.getMessage())
                        .build())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("MethodArgumentNotValidException - {}", ex.getMessage());
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("[%1$s [%2$s] %3$s]",
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse error = ErrorResponse.builder()
                .error(Error.builder()
                        .code(VALIDATION_ERROR_INVALID_INPUT_PARAM_FORMAT)
                        .msg(VALIDATION_FAILED)
                        .errors(errorList)
                        .build())
                .build();
        return handleExceptionInternal(ex, error, headers,HttpStatus.BAD_REQUEST, request);
    }
}
