package com.example.t_blog.exception;

public class LikeNotFoundException extends RuntimeException{


    public LikeNotFoundException(String message) {
        super(message);
    }
}
