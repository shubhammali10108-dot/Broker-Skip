package com.brokerskip.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "Authentication required or token is invalid.");
    }

    public UnauthorizedException() {
        super("Unauthorized access", HttpStatus.UNAUTHORIZED);
    }
}
