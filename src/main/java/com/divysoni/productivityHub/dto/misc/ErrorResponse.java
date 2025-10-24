package com.divysoni.productivityHub.dto.misc;

public record ErrorResponse(
        int status,
        String ErrorMsg,
        String details
) {
}
