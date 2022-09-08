package com.example.t_blog.repository;

import com.example.t_blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Optional<List<Comment>> findByCommentContainingIgnoreCase(String keyword);
}
