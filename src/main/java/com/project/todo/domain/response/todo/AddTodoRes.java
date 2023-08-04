package com.project.todo.domain.response.todo;

import com.project.todo.domain.factory.dtofactory.dto.TodoDto;
import com.project.todo.domain.types.TODO_TYPE;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddTodoRes {

    private Long memberId;
    private Long todoId;
    private TODO_TYPE type;
    private String title;
    private String content;

    public AddTodoRes(Long memberId, @NotNull TodoDto todoDto) {
        this.memberId = memberId;
        this.todoId = todoDto.getTodoId();
        this.type = todoDto.getType();
        this.title = todoDto.getTitle();
        this.content = todoDto.getContent();
    }
}
