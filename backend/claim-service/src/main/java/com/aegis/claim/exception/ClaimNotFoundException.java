package com.aegis.claim.exception;

public class ClaimNotFoundException extends RuntimeException {

    public ClaimNotFoundException(String message) {
        super(message);
    }

    public ClaimNotFoundException(Long id) {
        super("Claim not found with id: " + id);
    }

    public ClaimNotFoundException(String field, String value) {
        super("Claim not found with " + field + ": " + value);
    }
}
