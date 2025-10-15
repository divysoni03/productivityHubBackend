package com.divysoni.taskManager.dto;

public record ErrorResponse(
        int status,
        String msg,
        String details
) {
}
