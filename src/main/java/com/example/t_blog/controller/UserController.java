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


    @PostMapping(value = "create")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody PostDto postDto) {

        return new ResponseEntity<>(userService.createPost(postDto), CREATED);
    }

    @PostMapping(value = "comment/{user_id}/{post_id}")
    public ResponseEntity<CommentResponse> comment(@PathVariable(value = "user_id") String user_id, @PathVariable(value = "post_id") String post_id, @RequestBody CommentDTO commentDTO) {

        int userId = Integer.parseInt(user_id);
        int postId = Integer.parseInt(post_id);

        return new ResponseEntity<>(userService.comment(userId, postId, commentDTO), CREATED);
    }

    @PostMapping(value = "like/{user_id}/{post_id}")
    public ResponseEntity<LikeResponse> like(@PathVariable(value = "user_id") String user_id, @PathVariable(value = "post_id") String post_id, @RequestBody LikeDTO likeDTO) {

        int userId = Integer.parseInt(user_id);
        int postId = Integer.parseInt(post_id);

        return new ResponseEntity<>(userService.like(userId, postId, likeDTO), CREATED);
    }


    @PostMapping(value = "postSearch/{keyWord}")
    public ResponseEntity<PostSearchResponse> searchPost(@PathVariable(value = "keyWord") String keyword) {

        return new ResponseEntity<>(  userService.searchForPost(keyword), OK);
    }

    @PostMapping(value = "commentSearch/{keyWord}")
    public ResponseEntity<CommentSearchResponse> searchComment(@PathVariable(value = "keyWord") String keyword) {

        return new ResponseEntity<>(userService.searchForComment(keyword), OK);
    }

    @GetMapping(value = "posts")
    public ResponseEntity<AllPostsResponse> allPosts() {

        return new ResponseEntity<>(userService.allPosts(), OK);
    }

}
