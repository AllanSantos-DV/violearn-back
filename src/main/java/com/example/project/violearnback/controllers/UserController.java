package com.example.project.violearnback.controllers;

import com.example.project.violearnback.configs.JwtUtil;
import com.example.project.violearnback.entities.User;
import com.example.project.violearnback.enums.StatusCode;
import com.example.project.violearnback.services.UserService;
import com.example.project.violearnback.utils.StatusError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<Object> creteUser(@RequestBody User user) {
        if (userService.userExist(user.getEmail())) {
            StatusError response = new StatusError(StatusCode.USER_ALREADY_EXISTS);
            return ResponseEntity.badRequest().body(response);
        }
        userService.save(user);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtUtil.generateToken(user.getEmail()))
                .build();
	}

    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail(), user.getPassword())){
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(user.getEmail()))
                    .build();
        }
        StatusError response = new StatusError(StatusCode.EMAIL_OR_PASSWORD_INVALID);
        return ResponseEntity.badRequest().body(response);
    }

	@GetMapping("/top_five")
    public ResponseEntity<?> getTopFive(@RequestHeader(value = "Authorization") String token ) {
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        if (userService.userExist(email)) {
            return ResponseEntity.ok(userService.findTopFive());
        }
        StatusError response = new StatusError(StatusCode.EMAIL_OR_PASSWORD_INVALID);
        return ResponseEntity.badRequest().body(response);
    }

	@GetMapping("/perfil")
    public ResponseEntity<?> getById(@RequestHeader(value = "Authorization") String token) {
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        if (userService.userExist(email)) {
            return ResponseEntity.ok(userService.findByEmail(email));
        }
        StatusError response = new StatusError(StatusCode.EMAIL_OR_PASSWORD_INVALID);
        return ResponseEntity.badRequest().body(response);
	}

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestHeader(value = "Authorization") String token, @RequestBody User updatedUser) {
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        if (userService.userExist(email)) {
            return ResponseEntity.ok(userService.updateUser(email, updatedUser));
        }
        StatusError response = new StatusError(StatusCode.EMAIL_OR_PASSWORD_INVALID);
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteUser(@RequestHeader(value = "Authorization") String token) {
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        if (userService.userExist(email)) {
            userService.deleteByEmail(email);
            return ResponseEntity.ok().build();
        }
        StatusError response = new StatusError(StatusCode.EMAIL_OR_PASSWORD_INVALID);
        return ResponseEntity.badRequest().body(response);
    }
}
