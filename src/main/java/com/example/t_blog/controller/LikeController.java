package com.example.t_blog.controller;

import com.example.t_blog.dto.LikeDTO;
import com.example.t_blog.response.LikeResponse;
import com.example.t_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/api")
public class LikeController {

    private final UserService userService;

    @Autowired
    public LikeController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "like/{user_id}/{post_id}")
    public ResponseEntity<LikeResponse> like(@PathVariable(value = "user_id") String user_id, @PathVariable(value = "post_id") String post_id, @RequestBody LikeDTO likeDTO) {

        int userId = Integer.parseInt(user_id);
        int postId = Integer.parseInt(post_id);

        return new ResponseEntity<>(userService.like(userId, postId, likeDTO), CREATED);
    }

}
