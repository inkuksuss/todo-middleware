package com.project.todo.domain.request.todo;

import com.project.todo.domain.types.TODO_TYPE;
import lombok.Getter;

@Getter
public class UpdateTodoRequest {

    private TODO_TYPE type;

    private String title;

    private String content;
}
