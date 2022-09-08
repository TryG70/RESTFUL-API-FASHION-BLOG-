package com.example.t_blog.response;


import com.example.t_blog.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RegisterResponse {

    private String message;
    private LocalDate time;
    private UserDTO userDTO;
}
