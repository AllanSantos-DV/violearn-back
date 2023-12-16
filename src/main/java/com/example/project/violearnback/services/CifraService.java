package com.example.project.violearnback.services;

import com.example.project.violearnback.entities.Cifras;
import com.example.project.violearnback.entities.User;
import com.example.project.violearnback.repositories.CifrasRepository;
import com.example.project.violearnback.utils.SaveFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.example.project.violearnback.utils.SaveFile.saveFileInput;

@Service
public class CifraService {

    @Autowired
    private CifrasRepository cifrasRepository;

    public boolean saveCifra(MultipartFile file, String cifraJson, User user) {
        String path = "/dados/file/";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Cifras cifras = objectMapper.readValue(cifraJson, Cifras.class);
            cifras.setUser(user);
            cifras.setUserName(user.getName());
            cifras.setCifrasFile(saveFileInput(file, path));
            cifrasRepository.save(cifras);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteCifra(Long cifraId) {
        cifrasRepository.deleteById(cifraId);
    }

    @Transactional
    public void deleteAllCifraByUser(String email) {
        cifrasRepository.deleteAllByUserEmail(email);
    }

    public Page<Cifras> findAll(Pageable pageable) {
        return cifrasRepository.findAll(pageable);
    }

    public Page<Cifras> findAllByUserId(String email, Pageable pageable) {
        return cifrasRepository.findAllByUserEmail(email, pageable);
    }
}
