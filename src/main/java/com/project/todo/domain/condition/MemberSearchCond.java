package com.project.todo.domain.condition;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberSearchCond extends AbstractPageableDto {

    @Nullable
    private String name;

    @Nullable
    private String email;
}
