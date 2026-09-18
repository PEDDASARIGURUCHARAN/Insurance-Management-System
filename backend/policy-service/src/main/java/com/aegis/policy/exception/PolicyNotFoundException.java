package com.aegis.policy.exception;

public class PolicyNotFoundException extends RuntimeException {

    public PolicyNotFoundException(String message) {
        super(message);
    }

    public PolicyNotFoundException(Long id) {
        super("Policy not found with id: " + id);
    }

    public PolicyNotFoundException(String field, String value) {
        super("Policy not found with " + field + ": " + value);
    }
}
