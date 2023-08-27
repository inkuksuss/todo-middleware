package com.project.todo.domain.request.todo;

import com.project.todo.domain.types.TODO_TYPE;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateTodoRequest {

    @Nullable
    private TODO_TYPE type;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;
}
