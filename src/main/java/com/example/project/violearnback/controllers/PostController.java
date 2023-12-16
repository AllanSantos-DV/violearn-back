package com.example.project.violearnback.controllers;

import com.example.project.violearnback.configs.JwtUtil;
import com.example.project.violearnback.entities.Post;
import com.example.project.violearnback.entities.User;
import com.example.project.violearnback.services.PostService;
import com.example.project.violearnback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestHeader("Authorization") String token,
                                        @RequestParam("image") MultipartFile image,
                                        @RequestParam("post") String postJson) {
        if (userService.invalidToken(token)) return ResponseEntity.badRequest().build();
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        User user = userService.findByEmail(email);
        if (postService.savePost(image, postJson, user)) return ResponseEntity.ok().build();
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("page={page}/size={size}")
    public ResponseEntity<Page<Post>> getAllPost(@RequestHeader(value = "Authorization") String token,
                                                 @PathVariable int page,
                                                 @PathVariable int size) {
        if (userService.invalidToken(token)) return ResponseEntity.badRequest().build();
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @GetMapping("/user/page={page}/size={size}")
    public ResponseEntity<Page<Post>> getAllPostByUserEmail(@RequestHeader(value = "Authorization") String token,
                                                            @PathVariable int page,
                                                            @PathVariable int size) {
        if (userService.invalidToken(token)) return ResponseEntity.badRequest().build();
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.findAllByUserId(email, pageable));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> deletePost(@RequestHeader(value = "Authorization") String token,
                                             @PathVariable Long postId) {
        if (userService.invalidToken(token)) return ResponseEntity.badRequest().build();
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        User user = userService.findByEmail(email);
        if (postService.deletePost(user, postId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/user")
    public ResponseEntity<Object> deleteAllPostByUser(@RequestHeader(value = "Authorization") String token) {
        if (userService.invalidToken(token)) return ResponseEntity.badRequest().build();
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        postService.deleteAllPostByUser(email);
        return ResponseEntity.ok().build();
    }
}
