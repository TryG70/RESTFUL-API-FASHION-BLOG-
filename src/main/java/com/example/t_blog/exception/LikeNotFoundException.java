package com.example.t_blog.exception;

public class LikeNotFoundException extends RuntimeException{

    private String message;

    public LikeNotFoundException(String message) {
        this.message = message;
    }
}
