package com.example.project.violearnback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.violearnback.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserId(String userId);


}
