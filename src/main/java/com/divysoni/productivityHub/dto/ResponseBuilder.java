package com.divysoni.productivityHub.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    private ResponseBuilder() {/* Prevent instantiation */}

    public static <T> ResponseEntity<CustomResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(CustomResponse.success(message, data));
    }

    public static <T> ResponseEntity<CustomResponse<T>> created(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CustomResponse.success(message, data));
    }

    public static <T> ResponseEntity<CustomResponse<T>> failure(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(CustomResponse.failure(message));
    }
}
