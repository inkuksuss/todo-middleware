package com.project.todo.controller.request;

import jakarta.validation.constraints.Email;

public record MemberDetailRequest(

        Long memberId,
        String name,
        @Email String email,
        String password) {

}
