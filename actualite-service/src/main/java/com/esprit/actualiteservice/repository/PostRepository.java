package com.esprit.actualiteservice.repository;

import com.esprit.actualiteservice.model.Post;
import com.esprit.actualiteservice.model.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAll();
    List<Post> findAllByPostStatus(PostStatus status);
    void deleteByPostStatus(PostStatus status);
}
