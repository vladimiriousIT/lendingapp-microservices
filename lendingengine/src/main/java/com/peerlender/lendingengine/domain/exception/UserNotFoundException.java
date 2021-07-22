package com.peerlender.lendingengine.domain.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String username){
        super("User with username: " + username + " Not Found!");
    }
}
