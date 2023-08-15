package com.project.todo.domain.types;

public enum COMMON_TYPE {

    DELETE("Y"), ALIVE("N");

    private final String state;

    COMMON_TYPE(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
