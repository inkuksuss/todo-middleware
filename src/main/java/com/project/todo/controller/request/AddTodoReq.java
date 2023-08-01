package com.project.todo.controller.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AddTodoReq implements RequestObejct {
    private final Long userId;
    private final Long todoId;
    private final String todoTitle;
    private final String content;

    public AddTodoReq(Long userId, Long todoId, String todoTitle, String content) {
        this.userId = userId;
        this.todoId = todoId;
        this.todoTitle = todoTitle;
        this.content = content;
    }
}
