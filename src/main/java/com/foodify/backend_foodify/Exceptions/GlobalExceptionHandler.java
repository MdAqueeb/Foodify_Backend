package com.foodify.backend_foodify.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.foodify.backend_foodify.DTO.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(false, null, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(RestaurentNotActiveException.class)
    public ResponseEntity<ApiResponse<Object>> handleRestaurentNotActive(RestaurentNotActiveException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(false, null, ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AlreadyExiestException.class)
    public ResponseEntity<ApiResponse<Object>> handleAlreadyExist(AlreadyExiestException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(false, null, ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(false, null, "Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
