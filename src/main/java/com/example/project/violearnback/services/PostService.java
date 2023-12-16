package com.example.project.violearnback.services;

import com.example.project.violearnback.entities.Post;
import com.example.project.violearnback.entities.User;
import com.example.project.violearnback.repositories.PostRepository;
import com.example.project.violearnback.utils.SaveFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.example.project.violearnback.utils.SaveFile.saveFileInput;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Post> findAllByUserId(String email, Pageable pageable) {
        return postRepository.findAllByUserEmail(email, pageable);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    public void deleteAllPostByUser(String email) {
        postRepository.deleteAllByUserEmail(email);
    }

    public boolean savePost(MultipartFile image , String postJson, User user){
        String path = "/dados/imagens/posts/";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Post post = objectMapper.readValue(postJson, Post.class);
            post.setUser(user);
            post.setUserName(user.getName());
            post.setPostImage(saveFileInput(image, path));
            postRepository.save(post);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
