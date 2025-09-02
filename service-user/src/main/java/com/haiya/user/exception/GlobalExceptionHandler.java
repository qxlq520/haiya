package com.haiya.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> details = new HashMap<>();
        details.put("timestamp", new Date());
        details.put("message", ex.getMessage());
        details.put("details", request.getDescription(false));
        
        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Map<String, Object> details = new HashMap<>();
        details.put("timestamp", new Date());
        details.put("message", ex.getMessage());
        details.put("details", request.getDescription(false));
        
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}