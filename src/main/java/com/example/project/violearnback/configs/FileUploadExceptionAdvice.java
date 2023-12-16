package com.example.project.violearnback.configs;

import com.example.project.violearnback.enums.StatusCode;
import com.example.project.violearnback.utils.StatusError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException() {
        return ResponseEntity.badRequest().body(new StatusError(StatusCode.IMAGE_MAX_SIZE_EXCEEDED));
    }
}