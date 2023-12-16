package com.example.project.violearnback.repositories;

import com.example.project.violearnback.entities.Post;
import com.example.project.violearnback.entities.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUserEmail(String email, Pageable pageable);

    @NotNull Page<Post> findAll(@NotNull Pageable pageable);

    void deleteAllByUserEmail(String email);

    Optional<Post> findPostByUserAndId(User user, Long postId);
}
