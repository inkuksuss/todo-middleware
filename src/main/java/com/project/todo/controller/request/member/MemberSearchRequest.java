package com.project.todo.controller.request.member;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class MemberSearchRequest extends PageRequest {

    @Nullable
    private String name;

    @Nullable @Email
    private String email;
}
