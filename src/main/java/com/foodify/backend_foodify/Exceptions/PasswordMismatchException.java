package com.foodify.backend_foodify.Exceptions;

public class PasswordMismatchException extends RuntimeException {
    
    public PasswordMismatchException(String msg){
        super(msg);
    }
}
