package com.project.todo.domain.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import lombok.Getter;

@Getter
public class PageRequest {

    @Nullable
    private Integer page;

    @Nullable @Max(value = 20, message = "max size is 20")
    private Integer size;
}
