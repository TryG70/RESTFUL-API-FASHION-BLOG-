package com.example.t_blog.response;

import com.example.t_blog.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
public class CommentSearchResponse {

    private String message;
    private LocalDateTime time;
    private List<Comment> commentSearch;
}
