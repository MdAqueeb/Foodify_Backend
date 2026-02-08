package com.foodify.backend_foodify.Exceptions;

public class RestaurentNotActiveException extends RuntimeException {
    public RestaurentNotActiveException(String msg){
        super(msg);
    }
}
