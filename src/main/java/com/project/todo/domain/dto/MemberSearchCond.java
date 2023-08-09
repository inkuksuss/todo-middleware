package com.project.todo.domain.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberSearchCond {

    @Nullable
    private String name;

    @Nullable
    private String email;

    @Nullable
    private Integer page;

    @Nullable
    private Integer size;
}
