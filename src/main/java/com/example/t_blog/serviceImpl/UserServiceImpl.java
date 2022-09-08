package com.example.t_blog.serviceImpl;

import com.example.t_blog.dto.*;
import com.example.t_blog.exception.CommentNotFoundException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }

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
        return new RegisterResponse("success", LocalDate.now(), userDTO);
    }

    @Override
    public LoginResponse login(LoginDTO loginDTO)  {

        User user = findUserByEmail(loginDTO.getEmail());

        LoginResponse loginResponse = null;

        if(user != null) {
            if(user.getPassword().equals(loginDTO.getPassword())) {
                loginResponse = new LoginResponse("success", LocalDate.now());
            }
        } else {
            loginResponse = new LoginResponse("password mismatch", LocalDate.now());
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
        return new CreatePostResponse("success", LocalDate.now(), post);

    }

    @Override
    public CommentResponse comment(int user_id, int post_id, CommentDTO commentDTO) {

        User user = findUserById(user_id);
        Post post = findPostById(post_id);

        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);
        return new CommentResponse("success", LocalDate.now(), comment);
    }

    @Override
    public LikeResponse like(int user_id, int post_id, LikeDTO likeDTO) {

        User user = findUserById(user_id);
        Post post = findPostById(post_id);

        Like duplicateLike = likeRepository.findAllByUserAndPost(user, post);
        LikeResponse likeResponse;

        if (duplicateLike == null) {
            Like like = new Like();

            like.setUser(user);
            like.setPost(post);
            like.setLiked(likeDTO.isLiked());

            List<Like> likeList = likeRepository.findAllByPost(post);

            likeRepository.save(like);
            likeResponse = new LikeResponse("success", LocalDate.now(), like, likeList.size());

        } else {
            likeRepository.delete(duplicateLike);
            throw  new PostAlreadyLikedException("This post has already been liked, it is now Unliked");
        }

        return likeResponse;
    }

    @Override
    public PostSearchResponse searchForPost(String keyWord) {

        List<Post> postList = postRepository.findByTitleContainingIgnoreCase(keyWord).orElseThrow(() -> new PostNotFoundException("There's no match for the keyword: " + keyWord));

        return new PostSearchResponse("success", LocalDate.now(), postList);
    }

    @Override
    public CommentSearchResponse searchForComment(String keyWord) {

        List<Comment> commentList = commentRepository.findByCommentContainingIgnoreCase(keyWord).orElseThrow(() -> new CommentNotFoundException("Could not find any comment with the keyword: " + keyWord));

        return new CommentSearchResponse("success", LocalDate.now(), commentList);
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

    public AllPostsResponse allPosts() {
        List<Post> postList = postRepository.findAll();

        return new AllPostsResponse("success", LocalDate.now(), postList);
    }
}
