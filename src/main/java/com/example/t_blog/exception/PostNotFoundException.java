package com.example.t_blog.exception;

public class PostNotFoundException extends RuntimeException{


    private String message;

    public PostNotFoundException(String message) {
        this.message = message;
    }
}
