package com.example.t_blog.response;

import com.example.t_blog.model.Like;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class LikeResponse {

    private String message;
    private LocalDateTime time;
    private Like like;
    private int totalLikes;
}
