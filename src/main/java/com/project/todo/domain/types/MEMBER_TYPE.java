package com.project.todo.domain.types;

public enum MEMBER_TYPE {

    MEMBER("ROLE_USER"),
    MANAGER("ROLE_MANAGER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    MEMBER_TYPE(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
