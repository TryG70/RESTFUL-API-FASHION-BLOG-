package com.example.t_blog.response;

import com.example.t_blog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
public class PostSearchResponse {

    private String message;
    private LocalDateTime time;
    private List<Post> searchPost;
}
