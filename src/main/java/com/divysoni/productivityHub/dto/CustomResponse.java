package com.divysoni.productivityHub.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> CustomResponse<T> success(String message, T data) {
        return new CustomResponse<>(true, message, data);
    }

    public static <T> CustomResponse<T> failure(String message) {
        return new CustomResponse<>(false, message, null);
    }

}
