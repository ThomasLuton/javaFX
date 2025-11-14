package com.example.backlogtp.utils.exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(String nameIssue) {
        super(nameIssue);
    }
}
