package ru.hits.common.security.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }
    private String message;

    private LocalDateTime timestamp;

    private int status;
}
