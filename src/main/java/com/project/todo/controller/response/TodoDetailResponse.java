package com.project.todo.controller.response;

import com.project.todo.domain.types.TODO_TYPE;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class TodoDetailResponse {

    private Long memberId;

    private Long todoId;

    private TODO_TYPE todoType;

    private String todoTitle;

    private String todoContent;

    private LocalDateTime todoCreated;

    private LocalDateTime todoUpdate;
}
