package com.example.t_blog.service;

import com.example.t_blog.dto.*;
import com.example.t_blog.response.*;

public interface UserService {

    RegisterResponse signUp(UserDTO userDTO);

    LoginResponse login(LoginDTO loginDTO);

    CreatePostResponse createPost(PostDto postDto);

    CommentResponse comment(int user_id, int post_id, CommentDTO commentDTO);

    LikeResponse like(int user_id, int post_id, LikeDTO likeDTO);

    PostSearchResponse searchForPost(String title);

    CommentSearchResponse searchForComment(String keyWord);


}
