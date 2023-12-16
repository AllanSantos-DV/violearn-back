package com.example.project.violearnback.enums;

import lombok.Getter;

@Getter
public enum StatusCode {

    IMAGE_MAX_SIZE_EXCEEDED("Image max size exceeded"),
    IMAGE_FORMAT_NOT_SUPPORTED("Imagem não suportada, somente arquivos 'Jpeg', 'Webp' e 'Png'"),
    IMAGE_NOT_FOUND("Imagem não encontrada"),
    EMAIL_ALREADY_REGISTERED("Email já cadastrado"),
    EMAIL_NOT_FOUND("Email não encontrado"),
    EMAIL_OR_PASSWORD_INVALID("Email ou senha inválidos"),
    USER_NOT_FOUND("Usuário não encontrado"),
    USER_ALREADY_EXISTS("Usuário já cadastrado"),
    OK("Ok");








    private final String message;

    StatusCode(String message) {
        this.message = message;
    }
}
