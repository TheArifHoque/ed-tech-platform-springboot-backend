package com.arifhoque.commonmodule.exception;

import com.arifhoque.commonmodule.model.CustomHttpResponse;
import com.arifhoque.commonmodule.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomHttpResponse> handle4xxException(Exception e) {
        String errorMessage = null;
        if (e.getMessage() != null) {
            errorMessage = e.getMessage();
        } else if (e.getCause() != null) {
            errorMessage = e.getCause().toString();
        }
        return ResponseBuilder.buildFailureResponse(HttpStatus.UNAUTHORIZED, "401", errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomHttpResponse> handleException(Exception e) {
        String errorMessage = null;
        if (e.getMessage() != null) {
            errorMessage = e.getMessage();
        } else if (e.getCause() != null) {
            errorMessage = e.getCause().toString();
        }
        return ResponseBuilder.buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "500", errorMessage);
    }
}
