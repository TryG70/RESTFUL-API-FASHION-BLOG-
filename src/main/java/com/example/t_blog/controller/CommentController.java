package com.example.t_blog.controller;

import com.example.t_blog.dto.CommentDTO;
import com.example.t_blog.response.CommentResponse;
import com.example.t_blog.response.CommentSearchResponse;
import com.example.t_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/api")
public class CommentController {

    private final UserService userService;

    @Autowired
    public CommentController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "comment/{user_id}/{post_id}")
    public ResponseEntity<CommentResponse> comment(@PathVariable(value = "user_id") String user_id, @PathVariable(value = "post_id") String post_id, @RequestBody CommentDTO commentDTO) {

        int userId = Integer.parseInt(user_id);
        int postId = Integer.parseInt(post_id);

        return new ResponseEntity<>(userService.comment(userId, postId, commentDTO), CREATED);
    }


    @PostMapping(value = "commentSearch/{keyWord}")
    public ResponseEntity<CommentSearchResponse> searchComment(@PathVariable(value = "keyWord") String keyword) {

        return new ResponseEntity<>(userService.searchForComment(keyword), OK);
    }


}
