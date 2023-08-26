package com.project.todo.domain.request.member;

import com.project.todo.domain.request.PageRequest;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import lombok.Getter;

@Getter
public class MemberSearchRequest extends PageRequest {

    @Nullable
    private String name;

    @Nullable @Email
    private String email;
}
