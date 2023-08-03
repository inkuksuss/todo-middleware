package com.project.todo.exception;

public class NotFoundMemberException extends RuntimeException {

    public NotFoundMemberException() {}

    public NotFoundMemberException(String message) {
        super(message);
    }

    public NotFoundMemberException(Throwable cause) {
        super(cause);
    }
}
