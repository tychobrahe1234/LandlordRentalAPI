package com.example.landlordrentalapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SignatureVerificationFailureException extends RuntimeException {
    public SignatureVerificationFailureException(final String error) {
        super(error);
    }
}
