package com.example.t_blog.repository;

import com.example.t_blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<List<Post>> findByTitleContainingIgnoreCase(String keyword);
}
