package com.example.project.violearnback.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.violearnback.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserId(String userId);
}
