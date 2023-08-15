package com.project.todo.repository.todo;

import java.util.Optional;

public interface TodoRepositoryCustom {

    Long deleteDynamicTodo(Long todoId, Long memberId);

}
