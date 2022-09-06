package com.example.t_blog.exception;

public class UserNotFoundException extends RuntimeException{


    public UserNotFoundException(String message) {
        super(message);
    }
}
