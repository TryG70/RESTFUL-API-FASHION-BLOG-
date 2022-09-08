package com.example.t_blog.serviceImpl;

import com.example.t_blog.dto.*;
import com.example.t_blog.exception.CommentNotFoundException;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PostRepository postRepository;

    @Mock
    CommentRepository commentRepository;

    @Mock
    LikeRepository likeRepository;


    @InjectMocks
    UserServiceImpl userServiceImpl;

    private User user;
    private Post post;
    private Comment comment;

    private List<Post> postList = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();
    private List<Like> likeList = new ArrayList<>();


//    private LocalDateTime time;

    @BeforeEach
    void setUp() {

//        time = LocalDateTime.of(2022, SEPTEMBER, 7, 2, 20, 30, 3000);


        user = new User(1, "TryGod", "trygodnwakwasi@gmail.com", "12345", "Admin", LocalDate.now(), LocalDate.now(), postList, commentList, likeList);

        post = new Post(1, "Football", "I support manchester", "image.png", "football", LocalDate.now(), LocalDate.now(), user, commentList, likeList);
        postList.add(post);

        comment = new Comment(1, "Oh that's great", LocalDate.now(), LocalDate.now(), post, user);
        commentList.add(comment);



        when(userRepository.findUserByEmail("trygodnwakwasi@gmail.com")).thenReturn(Optional.of(user));
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        when(postRepository.findById(1)).thenReturn(Optional.ofNullable(post));



    }

    @Test
    void signUp() {

        UserDTO userDTO = new UserDTO("TryGod", "trygodnwakwasi@gmail.com", "12345", "Admin");

        var actual = userServiceImpl.signUp(userDTO);
        var expected = new RegisterResponse("success", LocalDate.now(), userDTO);

        assertEquals(expected, actual);
    }

    @Test
    void login() {

        LoginDTO loginDTO = new LoginDTO("trygodnwakwasi@gmail.com", "12345");

        var actual = userServiceImpl.login(loginDTO);
        var expected = new LoginResponse("success", LocalDate.now());

        assertEquals(expected, actual);

    }

    @Test
    void failedLogin_ThrowsUserNotFoundException()  throws UserNotFoundException {

        assertThrows( UserNotFoundException.class, ()->  userServiceImpl.login(new LoginDTO("amanda@gmail.com", "12345")));
    }

    @Test
    void createPost() {

        PostDto postDto = new PostDto("Football", "I support manchester", "image.png", 1);
        when(postRepository.save(post)).thenReturn(post);

        var actual = userServiceImpl.createPost(postDto);
        var expected = new CreatePostResponse("success", LocalDate.now(), post);

//        actual.setPost(post);
        actual.getPost().setId(1);
        actual.getPost().setCreatedAt(LocalDate.now());
        actual.getPost().setUpdatedAt(LocalDate.now());
        actual.getPost().setCommentList(commentList);


        assertEquals(expected, actual);
    }

    @Test
    void comment() {

        CommentDTO commentDTO = new CommentDTO("Oh that's great");

        var actual = userServiceImpl.comment(1, 1, commentDTO);
        var expected = new CommentResponse("success", LocalDate.now(), comment);

        actual.getComment().setId(1);
        actual.getComment().setCreatedAt(LocalDate.now());
        actual.getComment().setUpdatedAt(LocalDate.now());

        assertEquals(expected, actual);
    }

    @Test
    void like() {

        LikeDTO likeDTO = new LikeDTO(true);
        Like like = new Like(1, true, LocalDate.now(), LocalDate.now(), user, post);

        var actual = userServiceImpl.like(1, 1, likeDTO);
        var expected = new LikeResponse("success", LocalDate.now(), like, 0);

        actual.getLike().setId(1);
        actual.getLike().setCreatedAt(LocalDate.now());
        actual.getLike().setUpdatedAt(LocalDate.now());

        assertEquals(expected, actual);
    }

    @Test
    void searchForPost() {

        when(postRepository.findByTitleContainingIgnoreCase("Football")).thenReturn(Optional.of(postList));

        var actual = userServiceImpl.searchForPost("Football");
        var expected = new PostSearchResponse("success", LocalDate.now(), postList);

        assertEquals(expected, actual);

    }

    @Test
    void failedSearchForPost_ThrowsPostNotFoundException()  throws PostNotFoundException {

        assertThrows(PostNotFoundException.class, ()->  userServiceImpl.searchForPost("basketball"));
    }

    @Test
    void searchForComment() {

        when(commentRepository.findByCommentContainingIgnoreCase("Oh")).thenReturn(Optional.of(commentList));

        var actual = userServiceImpl.searchForComment("Oh");
        var expected = new CommentSearchResponse("success", LocalDate.now(), commentList);


        assertEquals(expected, actual);
    }

    @Test
    void failedSearchForComment_ThrowsCommentNotFoundException()  throws CommentNotFoundException {

        assertThrows(CommentNotFoundException.class, ()->  userServiceImpl.searchForComment("what"));
    }

    @Test
    void findUserById() {

        var actual = userServiceImpl.findUserById(1);
        var expected = user;

        assertEquals(expected, actual);
    }

    @Test
    void failedFindUserById_ThrowsUserNotFoundException()  throws UserNotFoundException {

        assertThrows(UserNotFoundException.class, ()->  userServiceImpl.findUserById(2));
    }

    @Test
    void findUserByEmail() {

        var actual = userServiceImpl.findUserByEmail("trygodnwakwasi@gmail.com");
        var expected = user;

        assertEquals(expected, actual);
    }

    @Test
    void failedFindUserByEmail_ThrowsUserNotFoundException()  throws UserNotFoundException {

        assertThrows(UserNotFoundException.class, ()->  userServiceImpl.findUserByEmail("amanda@gmail.com"));
    }

    @Test
    void findPostById() {

        var actual = userServiceImpl.findPostById(1);
        var expected = post;

        assertEquals(expected, actual);
    }

    @Test
    void failedFindPostById_ThrowsPostNotFoundException()  throws PostNotFoundException {

        assertThrows(PostNotFoundException.class, ()->  userServiceImpl.findPostById(2));
    }

    @Test
    void allPosts() {

        when(postRepository.findAll()).thenReturn(postList);

        var actual = userServiceImpl.allPosts();
        var expected = new AllPostsResponse("success", LocalDate.now(), postList);

        assertEquals(expected, actual);
    }
}