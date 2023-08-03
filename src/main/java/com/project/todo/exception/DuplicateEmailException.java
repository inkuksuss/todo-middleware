package com.project.todo.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException() {}

    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException(Throwable cause) {
        super(cause);
    }
}
