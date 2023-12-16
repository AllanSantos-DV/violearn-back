package com.example.project.violearnback.controllers;

import com.example.project.violearnback.configs.JwtUtil;
import com.example.project.violearnback.entities.Cifras;
import com.example.project.violearnback.entities.User;
import com.example.project.violearnback.services.CifraService;
import com.example.project.violearnback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cifra")
public class CifraController {

    @Autowired
    private CifraService cifraService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> criar(@RequestHeader(value = "Authorization") String token,
                                   @RequestParam("file") MultipartFile file,
                                   @RequestParam("cifra") String cifraJson) {
        if (userService.invalidToken(token)) return ResponseEntity.badRequest().build();
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        User user = userService.findByEmail(email);
        if (cifraService.saveCifra(file, cifraJson, user)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteCifra(@RequestHeader(value = "Authorization") String token,
                                              @RequestParam Long cifraId) {
        if (userService.invalidToken(token)) return ResponseEntity.badRequest().build();
        cifraService.deleteCifra(cifraId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user")
    public ResponseEntity<Object> deleteAllCifraByUser(@RequestHeader(value = "Authorization") String token) {
        if (userService.invalidToken(token)) return ResponseEntity.badRequest().build();
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        cifraService.deleteAllCifraByUser(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("page={page}/size={size}")
    public ResponseEntity<Page<Cifras>> getAllCifra(@RequestHeader(value = "Authorization") String token,
                                                    @PathVariable int page,
                                                    @PathVariable int size) {
        if (userService.invalidToken(token)) return ResponseEntity.badRequest().build();
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cifraService.findAll(pageable));
    }

    @GetMapping("/user/page={page}/size={size}")
    public ResponseEntity<Page<Cifras>> getAllCifrasByUserEmail(@RequestHeader(value = "Authorization") String token,
                                                                @PathVariable int page,
                                                                @PathVariable int size) {
        if (userService.invalidToken(token)) return ResponseEntity.badRequest().build();
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cifraService.findAllByUserId(email, pageable));
    }

}
