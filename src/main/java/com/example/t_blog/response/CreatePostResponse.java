package com.example.t_blog.response;

import com.example.t_blog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreatePostResponse {

    private String message;
    private LocalDateTime time;
    private Post post;
}
