package com.example.t_blog.response;


import com.example.t_blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RegisterResponse {

    private String message;
    private LocalDateTime time;
    private User user;
}
