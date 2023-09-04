package com.project.todo.repository.condition;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberSearchCond {

    @Nullable
    private String name;

    @Nullable
    private String email;
}
