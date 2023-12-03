package com.example.project.violearnback.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.violearnback.entities.Post;
import com.example.project.violearnback.repositories.PostRepository;
import com.example.project.violearnback.repositories.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {
	
	
	@Autowired
	PostRepository postRepo;
	
	@Autowired
	UserRepository repository;
	
	
	@PostMapping("")
	private Post submitUserPost(@RequestBody Post post) {
		return postRepo.save(post);
	}
	
	@GetMapping("")
	private ArrayList<Post> getAllPost(){
	
		ArrayList<Post> postList = postRepo.findAll();
		for (int i = 0; i < postList.size(); i++) {
			Post postItem = postList.get(i);
			postItem.setUserName(repository.findByUserId(postItem.getUserId()).getName());
		}
		
		return postList;
		
	}
}
