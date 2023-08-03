package com.project.todo.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MemberDetailResponse {

    private Long id;

    private String name;

    private String email;

    private LocalDateTime created;

    private LocalDateTime updated;
}
