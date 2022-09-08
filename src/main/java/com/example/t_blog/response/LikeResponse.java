package com.example.t_blog.response;

import com.example.t_blog.model.Like;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@Data
@AllArgsConstructor
public class LikeResponse {

    private String message;
    private LocalDate time;
    private Like like;
    private int totalLikes;
}
