package com.example.t_blog.response;

import com.example.t_blog.model.Post;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostResponse {

    private String message;
    private LocalDate time;
    private Post post;
}
