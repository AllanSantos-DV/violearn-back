package com.example.project.violearnback.repositories;

import com.example.project.violearnback.entities.Cifras;
import com.example.project.violearnback.entities.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CifrasRepository extends JpaRepository<Cifras, Long> {
    void deleteAllByUserEmail(String email);

    @NotNull Page<Cifras> findAll(@NotNull Pageable pageable);

    Page<Cifras> findAllByUserEmail(String email, Pageable pageable);
}
