package com.project.todo.domain.request;

import com.project.todo.domain.types.TODO_TYPE;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddTodoRequest {

    private Long todoId;

    private TODO_TYPE type;

    private String title;

    private String content;
}
