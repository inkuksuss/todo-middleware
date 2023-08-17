package com.project.todo.repository.todo;

import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface TodoRepositoryCustom {

    Long deleteDynamicTodo(Long todoId, Long memberId);

}
