package com.foodify.backend_foodify.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.foodify.backend_foodify.DTO.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex){
        ApiResponse<Object> response = new ApiResponse<>(false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceConflict(ResourceConflictException ex){
        ApiResponse<Object> response = new ApiResponse<>(false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidPasswordException(InvalidPasswordException ex){
        ApiResponse<Object> response = new ApiResponse<>(false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handlePasswordMismatchException(PasswordMismatchException ex){
        ApiResponse<Object> response = new ApiResponse<>(false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestaurentNotActiveException.class)
    public ResponseEntity<ApiResponse<Object>> handleRestaurentNotActive(RestaurentNotActiveException ex){
        ApiResponse<Object> response = new ApiResponse<>(false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
