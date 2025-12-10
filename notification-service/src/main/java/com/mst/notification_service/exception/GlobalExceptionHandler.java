package com.mst.notification_service.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handleAllExceptions(Exception ex) {
        System.out.println((Map.of("errors", Map.of("global", ex.getMessage()))));
    }
}
