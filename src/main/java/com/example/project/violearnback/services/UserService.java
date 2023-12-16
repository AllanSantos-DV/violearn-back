package com.example.project.violearnback.services;

import com.example.project.violearnback.configs.JwtUtil;
import com.example.project.violearnback.entities.Cifras;
import com.example.project.violearnback.entities.Post;
import com.example.project.violearnback.entities.User;
import com.example.project.violearnback.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void save(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    @Transactional
    public User updateUser(String email, User updatedUser) {
        User user = findByEmail(email);
        user.setName(updatedUser.getName());
        user.setUserName(updatedUser.getUserName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        updateUserNameInPosts(user.getId(), updatedUser.getUserName());
        updateUserNameInCifras(user.getId(), updatedUser.getUserName());
        return userRepository.save(user);
    }

    public void updateUserNameInPosts(Long userId, String userName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Post> update = cb.createCriteriaUpdate(Post.class);
        Root<Post> post = update.from(Post.class);
        update.set("userName", userName);
        update.where(cb.equal(post.get("user").get("id"), userId));
        entityManager.createQuery(update).executeUpdate();
    }

    public void updateUserNameInCifras(Long userId, String userName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Cifras> updateCifras = cb.createCriteriaUpdate(Cifras.class);
        Root<Cifras> cifras = updateCifras.from(Cifras.class);
        updateCifras.set("userName", userName);
        updateCifras.where(cb.equal(cifras.get("user").get("id"), userId));
        entityManager.createQuery(updateCifras).executeUpdate();
    }

    public boolean findByEmail(String email, String senha) {
        return Optional.ofNullable(userRepository.findUserByEmail(email))
                .filter(user -> passwordEncoder.matches(senha, user.getPassword()))
                .isPresent();
    }

    public boolean userExist(String email) {
        return Optional.ofNullable(userRepository.findUserByEmail(email))
                .isPresent();
    }

    public List<User> findTopFive() {
        List<User> users = userRepository.findTop5ByPostsAndNotes();
        return users.subList(0, Math.min(users.size(), 5));
    }


    public boolean invalidToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) return true;
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        if (userRepository.findUserByEmail(email) == null) return true;
        return !jwtUtil.validateToken(token.substring(7), email);
    }
}
