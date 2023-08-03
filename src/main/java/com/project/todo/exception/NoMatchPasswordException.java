package com.project.todo.exception;

public class NoMatchPasswordException extends RuntimeException {

    public NoMatchPasswordException() {}

    public NoMatchPasswordException(String message) {
        super(message);
    }

    public NoMatchPasswordException(Throwable cause) {
        super(cause);
    }
}
