package com.foodify.backend_foodify.Exceptions;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String msg){
        super(msg);
    }
}
