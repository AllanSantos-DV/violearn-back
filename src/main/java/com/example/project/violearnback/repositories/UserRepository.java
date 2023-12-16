package com.example.project.violearnback.repositories;

import com.example.project.violearnback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    List<User> findTop5ByPostsIsNotNullAndNotesIsNotNullOrderByPostsDescNotesDesc();

    void deleteByEmail(String email);
}
