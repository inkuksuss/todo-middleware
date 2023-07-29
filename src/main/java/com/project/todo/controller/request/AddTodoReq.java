package com.project.todo.controller.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AddTodoReq {
    private final Long userId;
    private final Long todoId;
    private final String content;

    public AddTodoReq(Long userId, Long todoId, String content) {
        this.userId = userId;
        this.todoId = todoId;
        this.content = content;
    }
}
