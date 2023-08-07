package com.project.todo.domain.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;


@Getter
public class MemberDetailRequest {

    private final Long memberId;

    private final String name;

    @Email
    private final String email;

    private final String password;

    public MemberDetailRequest(Long memberId, String name, String email, String password) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
