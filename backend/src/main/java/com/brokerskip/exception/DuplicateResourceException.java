package com.brokerskip.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends ApiException {

    public DuplicateResourceException(String resource, String field, Object value) {
        super(
            String.format("%s already exists with %s: '%s'", resource, field, value),
            HttpStatus.CONFLICT,
            String.format("A %s with %s '%s' already exists in the system.", resource, field, value)
        );
    }

    public DuplicateResourceException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
