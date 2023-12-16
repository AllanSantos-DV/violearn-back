package com.example.project.violearnback.repositories;

import com.example.project.violearnback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    @Query(value = "SELECT u FROM users u ORDER BY (SIZE(u.posts) + SIZE(u.notes)) DESC")
    List<User> findTop5ByPostsAndNotes();


    void deleteByEmail(String email);
}
