package com.project.todo.controller.request.todo;

import com.project.todo.domain.types.TODO_TYPE;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddTodoRequest {

    private TODO_TYPE type;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;
}
