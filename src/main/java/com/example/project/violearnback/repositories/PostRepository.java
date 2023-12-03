package com.example.project.violearnback.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.project.violearnback.entities.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

	@SuppressWarnings("unchecked")
	Post save(Post post);
	ArrayList<Post> findAll();
	
}
