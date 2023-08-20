package com.project.todo.domain.types;

public enum RESPONSE_CODE {

    SUCCESS(0),
    DUPLICATE_EMAIL(1),
    NO_MATCH_PASSWORD(2),
    NOT_FOUND_MEMBER(3),
    INVALID_PARAMETER(4),
    INVALID_STATE(5),
    ACCESS_DENIED(6),
    NOT_UNIQUE(7),
    EXCEPTION(99);

    private final int code;

    RESPONSE_CODE(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
