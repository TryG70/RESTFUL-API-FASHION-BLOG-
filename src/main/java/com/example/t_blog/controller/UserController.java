package com.example.t_blog.controller;

import com.example.t_blog.dto.*;
import com.example.t_blog.response.*;
import com.example.t_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "register")
    public ResponseEntity<RegisterResponse> register(@RequestBody UserDTO userDTO) {

        return new ResponseEntity<>(userService.signUp(userDTO), CREATED);
    }

    @PostMapping(value = "login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {

        return new ResponseEntity<>(userService.login(loginDTO), OK);
    }


}
