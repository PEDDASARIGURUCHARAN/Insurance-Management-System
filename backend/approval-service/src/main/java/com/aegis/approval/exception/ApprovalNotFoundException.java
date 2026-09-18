package com.aegis.approval.exception;

public class ApprovalNotFoundException extends RuntimeException {

    public ApprovalNotFoundException(String message) {
        super(message);
    }

    public ApprovalNotFoundException(Long id) {
        super("Approval not found with id: " + id);
    }

    public ApprovalNotFoundException(String field, String value) {
        super("Approval not found with " + field + ": " + value);
    }
}
