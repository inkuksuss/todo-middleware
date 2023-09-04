package com.project.todo.controller.response.todo;

import com.project.todo.domain.types.TODO_TYPE;

import java.time.LocalDateTime;

public class TodoDetail {

    private Long memberId;

    private String memberName;

    private Long todoId;

    private TODO_TYPE type;

    private String title;

    private String content;

    private String isComplete;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Long createdBy;

    private Long updatedBy;
}
