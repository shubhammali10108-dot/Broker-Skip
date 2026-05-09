package com.brokerskip.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiException extends RuntimeException {

    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String details;

    public ApiException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ApiException(String message, HttpStatus status, String details) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }
}
