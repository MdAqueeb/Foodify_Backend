package com.foodify.backend_foodify.Exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Password is Invalid");
    }

    public InvalidPasswordException(String msg){
        super(msg);
    }
}
