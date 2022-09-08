package com.example.t_blog.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String message;
    private LocalDate time;

}
