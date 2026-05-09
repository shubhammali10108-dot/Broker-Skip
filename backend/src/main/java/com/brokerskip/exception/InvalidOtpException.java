package com.brokerskip.exception;

import org.springframework.http.HttpStatus;

public class InvalidOtpException extends ApiException {

    public InvalidOtpException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "The provided OTP is invalid or has expired.");
    }

    public InvalidOtpException() {
        super("Invalid or expired OTP", HttpStatus.BAD_REQUEST);
    }
}
