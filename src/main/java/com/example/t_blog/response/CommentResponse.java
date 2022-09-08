package com.example.t_blog.response;

import com.example.t_blog.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@Data
@AllArgsConstructor
public class CommentResponse {

    private String message;
    private LocalDate time;
    private Comment comment;
}
