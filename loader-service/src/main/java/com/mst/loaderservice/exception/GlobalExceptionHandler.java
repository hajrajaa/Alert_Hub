package com.mst.loaderservice.exception;

import org.eclipse.jgit.api.errors.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CheckedExceptionWrapper.class)
    public ResponseEntity<String> handleCheckedException(CheckedExceptionWrapper e) {
        Throwable cause = e.getCause();

        if (cause instanceof TransportException) {
            // Network issues or authentication failure
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Cannot reach remote repository: " + cause.getMessage());
        }
        if (cause instanceof InvalidRemoteException) {
            // Invalid repository URL
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid remote URL");
        }
        if (cause instanceof RefNotFoundException) {
            // Branch not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Branch not found");
        }if (cause instanceof CheckoutConflictException) {
            // Conflict when checking out branch
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Checkout conflict: " + e.getMessage());
        }if (cause instanceof GitAPIException) {
            // Other JGit errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Git operation failed: " + e.getMessage());
        }if (cause instanceof IOException) {
            // Other JGit errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("IOException: " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getClass().getSimpleName() + ": " + cause.getMessage());
    }

    // Invalid arguments
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid parameters: " + e.getMessage());
    }

    // File system permissions
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleSecurityException(SecurityException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Permission denied: " + e.getMessage());
    }

    // General Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getClass().getSimpleName() + ":" + e.getMessage());
    }
}
