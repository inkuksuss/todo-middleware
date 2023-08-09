package com.project.todo.domain.request;

import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class MemberSearchRequest {

    @Nullable
    private String name;

    @Nullable
    private String email;

    @Nullable
    private Integer page;

    @Nullable
    private Integer size;

    public MemberSearchRequest() {}
}
