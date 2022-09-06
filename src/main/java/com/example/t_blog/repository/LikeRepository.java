package com.example.t_blog.repository;

import com.example.t_blog.model.Like;
import com.example.t_blog.model.Post;
import com.example.t_blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

    Like findAllByUserAndPost(User user, Post post);

    List<Like> findAllByPost(Post post);
}
