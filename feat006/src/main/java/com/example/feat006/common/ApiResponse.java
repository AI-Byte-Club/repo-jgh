package com.example.feat006.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private long elapsedMs;

    public static <T> ApiResponse<T> of(boolean success, String message, T data, long elapsedMs) {
        return new ApiResponse<>(success, message, data, elapsedMs);
    }
}
