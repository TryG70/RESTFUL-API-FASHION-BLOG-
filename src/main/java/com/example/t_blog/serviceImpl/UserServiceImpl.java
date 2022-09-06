package com.example.t_blog.serviceImpl;

import com.example.t_blog.dto.*;
import com.example.t_blog.exception.PostAlreadyLikedException;
import com.example.t_blog.exception.PostNotFoundException;
import com.example.t_blog.exception.UserNotFoundException;
import com.example.t_blog.model.Comment;
import com.example.t_blog.model.Like;
import com.example.t_blog.model.Post;
import com.example.t_blog.model.User;
import com.example.t_blog.repository.CommentRepository;
import com.example.t_blog.repository.LikeRepository;
import com.example.t_blog.repository.PostRepository;
import com.example.t_blog.repository.UserRepository;
import com.example.t_blog.response.*;
import com.example.t_blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private LikeRepository likeRepository;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");


    @Override
    public RegisterResponse signUp(UserDTO userDTO) {

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        userRepository.save(user);
        return new RegisterResponse("success", LocalDateTime.now(), user);
    }

    @Override
    public LoginResponse login(LoginDTO loginDTO)  {

        User user = findUserByEmail(loginDTO.getEmail());

        LoginResponse loginResponse = null;

        if(user != null) {
            if(user.getPassword().equals(loginDTO.getPassword())) {
                loginResponse = new LoginResponse("success", LocalDateTime.now());
            }
        } else {
            loginResponse = new LoginResponse("password mismatch", LocalDateTime.now());
        }

        return loginResponse;
    }

    public String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public CreatePostResponse createPost(PostDto postDto) {

        Post post = new Post();

        User user = findUserById(postDto.getUser_id());
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setImage(postDto.getImage());
        post.setSlug(toSlug(postDto.getTitle()));
        post.setUser(user);

        postRepository.save(post);
        return new CreatePostResponse("success", LocalDateTime.now(),post);

    }

    @Override
    public CommentResponse comment(int user_id, int post_id, CommentDTO commentDTO) {

        User user = findUserById(user_id);
        Post post = findPostById(post_id);

        Comment comment = new Comment();
        comment.setComment(comment.getComment());
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);
        return new CommentResponse("success", LocalDateTime.now(), comment);
    }

    @Override
    public LikeResponse like(int user_id, int post_id, LikeDTO likeDTO) {

        User user = findUserById(user_id);
        Post post = findPostById(post_id);

        Like duplicateLike = likeRepository.findAllByUserAndPost(user, post);
        LikeResponse likeResponse = null;

        if (duplicateLike == null) {
            Like like = new Like();

            like.setUser(user);
            like.setPost(post);
            like.setLiked(likeDTO.isLiked());

            List<Like> likeList = likeRepository.findAllByPost(post);

            likeRepository.save(like);
            likeResponse = new LikeResponse("success", LocalDateTime.now(), like, likeList.size());

        } else {
            likeRepository.delete(duplicateLike);
            throw  new PostAlreadyLikedException("This post has already been liked, it is now Unliked");
        }

        return likeResponse;
    }

    @Override
    public PostSearchResponse searchForPost(String keyWord) {

        List<Post> postList = postRepository.findByTitleContaining(keyWord);

        return new PostSearchResponse("success", LocalDateTime.now(), postList);
    }

    @Override
    public CommentSearchResponse searchForComment(String keyWord) {

        List<Comment> commentList = commentRepository.findByCommentContaining(keyWord);

        return new CommentSearchResponse("success", LocalDateTime.now(), commentList);
    }

    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID: " + id + " Not Found"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User with Email: " + email + " Not Found"));
    }

    public Post findPostById(int id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post with ID: " + id + " Not Found"));
    }
}
