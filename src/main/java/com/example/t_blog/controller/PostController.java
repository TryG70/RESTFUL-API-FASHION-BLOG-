package com.example.t_blog.controller;

import com.example.t_blog.dto.PostDto;
import com.example.t_blog.response.AllPostsResponse;
import com.example.t_blog.response.CreatePostResponse;
import com.example.t_blog.response.PostSearchResponse;
import com.example.t_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/api")
public class PostController {

    private final UserService userService;

    @Autowired
    public PostController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping(value = "create")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody PostDto postDto) {

        return new ResponseEntity<>(userService.createPost(postDto), CREATED);
    }

    @PostMapping(value = "postSearch/{keyWord}")
    public ResponseEntity<PostSearchResponse> searchPost(@PathVariable(value = "keyWord") String keyword) {

        return new ResponseEntity<>(  userService.searchForPost(keyword), OK);
    }


    @GetMapping(value = "posts")
    public ResponseEntity<AllPostsResponse> allPosts() {

        return new ResponseEntity<>(userService.allPosts(), OK);
    }

}
