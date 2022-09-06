package com.example.t_blog.exception;

public class CommentNotFoundException extends RuntimeException{


    public CommentNotFoundException(String message) {
        super(message);
    }
}
