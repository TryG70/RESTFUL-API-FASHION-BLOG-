package com.example.t_blog.response;

import com.example.t_blog.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class CommentResponse {

    private String message;
    private LocalDateTime time;
    private Comment comment;
}
