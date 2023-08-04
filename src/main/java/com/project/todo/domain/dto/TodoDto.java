package com.project.todo.domain.dto;

import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.entity.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@ToString
public class TodoDto implements CustomDto {

    private Long memberId;
    private Long todoId;
    private TODO_TYPE type;
    private String title;
    private String content;
    private String isDelete;
    private LocalDateTime created;
    private LocalDateTime updated;

    public static TodoDto fromEntity(Todo todo) {
        TodoDto todoDto = new TodoDto();
        todoDto.memberId = todo.getMember().getId();
        todoDto.todoId = todo.getId();
        todoDto.type = todo.getType();
        todoDto.title = todo.getTitle();
        todoDto.content = todo.getContent();
        todoDto.isDelete = todo.getIsDelete();
        todoDto.created = todo.getCreated();
        todoDto.updated = todo.getUpdated();

        return todoDto;
    }
}
