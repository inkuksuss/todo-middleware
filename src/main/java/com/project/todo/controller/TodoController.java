package com.project.todo.controller;

import com.project.todo.aop.annotation.Login;
import com.project.todo.config.argument_resolver.annotation.LoginId;
import com.project.todo.domain.dto.TodoDto;
import com.project.todo.domain.request.AddTodoRequest;
import com.project.todo.domain.response.common.ResponseResult;
import com.project.todo.domain.types.RESPONSE_CODE;
import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/save")
    public ResponseEntity<ResponseResult<Long>> addTodo(
            @RequestBody @Validated AddTodoRequest request,
            @LoginId Long memberId
    ) {
        TodoDto dto = new TodoDto();
        dto.setMemberId(memberId);
        dto.setTodoId(request.getTodoId());
        dto.setType(TODO_TYPE.COMMON);
        dto.setTitle(request.getTitle());
        dto.setContent(request.getContent());

        TodoDto todoDto = todoService.saveTodo(dto);

        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.SUCCESS, null, todoDto.getTodoId()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<ResponseResult<Void>> addTodo(
            @PathVariable Long todoId,
            @LoginId Long memberId
    ) {

        if (todoId == null) {
            throw new IllegalArgumentException("todo id can not be null");
        }

        if (memberId == null) {
            throw new IllegalArgumentException("member id can not be null");
        }

        todoService.removeTodo(memberId, todoId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
