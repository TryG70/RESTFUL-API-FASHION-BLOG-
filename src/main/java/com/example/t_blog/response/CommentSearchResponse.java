package com.example.t_blog.response;

import com.example.t_blog.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
public class CommentSearchResponse {

    private String message;
    private LocalDate time;
    private List<Comment> commentSearch;
}
