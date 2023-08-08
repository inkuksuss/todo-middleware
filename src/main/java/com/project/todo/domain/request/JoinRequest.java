package com.project.todo.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class JoinRequest {

    @NotBlank @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public JoinRequest() {}

    public JoinRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
