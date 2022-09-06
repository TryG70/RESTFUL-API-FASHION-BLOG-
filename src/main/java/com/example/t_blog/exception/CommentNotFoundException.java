package com.example.t_blog.exception;

public class CommentNotFoundException extends RuntimeException{

    private String message;

    public CommentNotFoundException(String message) {
        this.message = message;
    }
}
