package com.example.t_blog.controller;

import com.example.t_blog.exception.CommentNotFoundException;
import com.example.t_blog.exception.PostAlreadyLikedException;
import com.example.t_blog.exception.PostNotFoundException;
import com.example.t_blog.exception.UserNotFoundException;
import com.example.t_blog.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFound(UserNotFoundException exception) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(exceptionResponse, NOT_FOUND);
    }


    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Object> postNotFound(PostNotFoundException exception) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(exceptionResponse, NOT_FOUND);
    }


    @ExceptionHandler(PostAlreadyLikedException.class)
    public ResponseEntity<Object> postAlreadyLiked(PostAlreadyLikedException exception) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), TOO_MANY_REQUESTS);
        return new ResponseEntity<>(exceptionResponse, TOO_MANY_REQUESTS);
    }


    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Object> commentNotFound(CommentNotFoundException exception) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(exceptionResponse, NOT_FOUND);
    }
}
