package com.example.project.violearnback.utils;

import com.example.project.violearnback.enums.StatusCode;
import lombok.Data;

@Data
public class StatusError {

    private final String error;
    private final String message;

    public StatusError(StatusCode errorCode) {
        this.error = errorCode.name();
        this.message = errorCode.getMessage();
    }
}