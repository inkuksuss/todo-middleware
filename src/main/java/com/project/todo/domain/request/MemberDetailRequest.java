package com.project.todo.domain.request;

import jakarta.validation.constraints.Email;

public record MemberDetailRequest(

        Long memberId,
        String name,
        @Email String email,
        String password) {

}
