package com.project.todo.domain.request;

import com.project.todo.domain.types.TODO_TYPE;
import jakarta.validation.constraints.NotNull;

public record TodoDetailRequest(

        Long memberId,
        @NotNull Long todoId,
        TODO_TYPE type,
        String title,
        String content) {

}
