package com.example.t_blog.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    private String role;
}
