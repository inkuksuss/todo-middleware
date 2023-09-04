package com.project.todo.repository.todo;

import com.project.todo.repository.condition.TodoSearchCond;
import com.project.todo.service.dto.PageDto;
import com.project.todo.service.dto.todo.TodoDto;
import org.springframework.data.domain.Pageable;

public interface TodoRepositoryCustom {

    Long deleteDynamicTodo(Long todoId, Long memberId);

    PageDto<TodoDto> findTodoListOfMember(TodoSearchCond cond, Pageable pageable);
}
