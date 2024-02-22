package com.quiet.onterviewstorage.global;

import java.io.IOException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalController {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseExceptionResponse> handleBaseException(BaseException e) {
        log.info("# error: " + e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(new BaseExceptionResponse(
                        e.getErrorCode().getStatus().value(),
                        e.getErrorCode().getMessage()
                ));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<BaseExceptionResponse> handleIOException(IOException e) {
        log.info("# IO error: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new BaseExceptionResponse(
                        HttpStatus.FORBIDDEN.value(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseExceptionResponse> handleException(Exception e) {
        log.info("# Internal error: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseExceptionResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>("Invalid method", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException ex) {
        return new ResponseEntity<>("Unsupported media type", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new ResponseEntity<>("No handler found", HttpStatus.NOT_FOUND);
    }
}
