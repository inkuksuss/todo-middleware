package com.project.todo.domain.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import lombok.Getter;

@Getter
public class MemberSearchRequest {

    @Nullable
    private String name;

    @Nullable @Email
    private String email;

    @Nullable
    private Integer page;

    @Nullable @Max(value = 20, message = "hello")
    private Integer size;

    public MemberSearchRequest() {}
}
