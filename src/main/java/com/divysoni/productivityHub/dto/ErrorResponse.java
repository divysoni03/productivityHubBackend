package com.divysoni.productivityHub.dto;

public record ErrorResponse(
        int status,
        String ErrorMsg,
        String details
) {
}
