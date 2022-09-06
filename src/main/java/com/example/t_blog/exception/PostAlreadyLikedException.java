package com.example.t_blog.exception;

public class PostAlreadyLikedException extends RuntimeException{

    private String message;

    public PostAlreadyLikedException(String message) {
        this.message = message;
    }
}
